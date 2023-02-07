package com.recrutement.modules.verifToken.service;

import com.querydsl.core.BooleanBuilder;
import com.recrutement.exceptions.DataNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.recrutement.utils.DateHandler;
import com.recrutement.utils.PasswordHandler;
import com.recrutement.config.ExternalConfigs;
import com.recrutement.exceptions.TokenExpiredException;
import com.recrutement.modules.user.User;
import com.recrutement.modules.verifToken.QVerifToken;
import com.recrutement.modules.verifToken.VerifToken;
import com.recrutement.modules.verifToken.VerifTokenRepository;
import com.recrutement.modules.verifToken.VerifTokenType;

import jakarta.transaction.Transactional;
import java.util.Date;
import java.util.Optional;

@Service
public class VerifTokenService implements IVerifTokenService {

    @Autowired
    private VerifTokenRepository verifTokenRepository;
    @Autowired
    private ExternalConfigs externalConfigs;

    @Override
    public VerifToken create(VerifTokenType type, User user) {
        VerifToken verifToken = new VerifToken();
        verifToken.setType(type);
        verifToken.setUser(user);
        setValueAndExp(verifToken, type);
        return verifTokenRepository.save(verifToken);
    }

    @Override
    @Transactional
    public User verify(VerifTokenType type, String token) throws TokenExpiredException, DataNotFoundException {
        QVerifToken qVerifToken = QVerifToken.verifToken;
        BooleanBuilder where = new BooleanBuilder();
        where.and(qVerifToken.type.eq(type));
        where.and(qVerifToken.value.eq(token));
        Optional<VerifToken> verifTokenOptional =
                verifTokenRepository.findVerifTokenByTypeAndValue(type, token); //.findOne(where.getValue());
        if(verifTokenOptional.isEmpty()){
            throw new DataNotFoundException("Token not found");
        }
        VerifToken verifToken = verifTokenOptional.get();
        Date now = new Date();
        if(!now.before(verifToken.getExpiration())){
            verifTokenRepository.deleteById(verifToken.getId());
            throw new TokenExpiredException("Token expired");
        }
        verifTokenRepository.deleteById(verifToken.getId());
        return verifToken.getUser();
    }

    @Override
    public User findUserByIdAndDelete(Long id) throws DataNotFoundException {
        Optional<VerifToken> verifTokenOptional = verifTokenRepository.findById(id);
        if(verifTokenOptional.isEmpty()){
            throw new DataNotFoundException("Token not found");
        }
        VerifToken verifToken = verifTokenOptional.get();
        User response = verifToken.getUser();
        verifTokenRepository.deleteById(verifToken.getId());
        return response;
    }

    private void setValueAndExp(VerifToken token, VerifTokenType type){
        switch(type) {
            case MFAAUTH:
                token.setExpiration(DateHandler.addMinutesToCurrentDate(
                        externalConfigs.getMFATokenExpiration().longValue()
                ));
                token.setValue(PasswordHandler.generateOTP());
                break;
            case ACCOUNTACTIVATE:
                token.setExpiration(DateHandler.addMinutesToCurrentDate(
                        externalConfigs.getAccountActivationTokenExpiration().longValue()
                ));
                token.setValue(PasswordHandler.generateRandomToken(16));
                break;
            case FORGETPASSWORD:
                token.setExpiration(DateHandler.addMinutesToCurrentDate(
                        externalConfigs.getForgotPasswordTokenExpiration().longValue()
                ));
                token.setValue(PasswordHandler.generateRandomToken(16));
                break;
        }
    }
}
