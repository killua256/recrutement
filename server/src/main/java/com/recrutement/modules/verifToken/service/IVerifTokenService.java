package com.recrutement.modules.verifToken.service;

import com.recrutement.exceptions.DataNotFoundException;
import com.recrutement.exceptions.TokenExpiredException;
import com.recrutement.modules.user.User;
import com.recrutement.modules.verifToken.VerifToken;
import com.recrutement.modules.verifToken.VerifTokenType;

public interface IVerifTokenService {
    VerifToken create(VerifTokenType type, User user);

    User verify(VerifTokenType type, String token) throws TokenExpiredException, DataNotFoundException;

    User findUserByIdAndDelete(Long id) throws DataNotFoundException;
}
