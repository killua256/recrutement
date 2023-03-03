package com.recrutement.modules.auth.service;

import com.recrutement.exceptions.*;
import com.recrutement.modules.auth.httpRequest.SignupRequest;
import com.recrutement.modules.role.IRoleService;
import com.recrutement.modules.user.dto.ChangePasswordReq;
import com.recrutement.modules.verifToken.VerifToken;
import com.recrutement.modules.verifToken.VerifTokenType;
import com.recrutement.modules.verifToken.service.IVerifTokenService;
import com.recrutement.utils.PasswordHandler;
import com.recrutement.utils.UtilsService;
import com.recrutement.utils.email.IEmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.recrutement.config.ExternalConfigs;
import com.recrutement.modules.auth.httpRequest.AuthRequest;
import com.recrutement.modules.auth.httpResponse.AuthResponse;
import com.recrutement.modules.user.User;
import com.recrutement.modules.user.UserMapper;
import com.recrutement.modules.user.UserRepository;
import com.recrutement.modules.user.dto.UserDto;
import com.recrutement.modules.user.service.IUserService;
import com.recrutement.security.jwt.JwtProvider;
import com.recrutement.security.jwt.JwtResponse;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
public class AuthService implements IAuthService {

    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private JwtProvider jwtProvider;
    @Autowired
    private IUserService userService;
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private UtilsService utilsService;
    @Autowired
    private IRoleService roleService;
    @Autowired
    private IVerifTokenService verifTokenService;
    @Autowired
    private IEmailService emailService;
    @Autowired
    private ExternalConfigs externalConfigs;

    @Override
    @Transactional
    public UserDto signup(SignupRequest user) throws UserAlreadyExistsException {
        if (userService.existsByUsername(user.getUsername())) {
            throw new UserAlreadyExistsException("Username already used");
        }
        if (userService.existsByEmail(user.getEmail())) {
            throw new UserAlreadyExistsException("Email already used");
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRole(roleService.findByName("ROLE_USER"));
        User toUser = userMapper.sigReqToUser(user);
        return userService.save(toUser);
    }

    @Override
    public void sendAccountActivation(UserDto user){
        String activationToken = verifTokenService.create(
                VerifTokenType.ACCOUNTACTIVATE,
                userMapper.toUser(user)
        ).getValue();
        if(activationToken != null){
            Map<String, String> data = new HashMap<>();
            data.put("activationLink", externalConfigs.getClientUrl() + "activate/" + activationToken);
            data.put("username", user.getUsername());
            emailService.sendActivationEmail(
                    user.getEmail(),
                    data
            );
        }
    }

    @Override
    @Transactional
    public Boolean activateAccount(String token) throws TokenExpiredException, DataNotFoundException {
        User user = verifTokenService.verify(VerifTokenType.ACCOUNTACTIVATE, token.replaceAll("^\"|\"$", ""));
        user.setActivatedAt(new Date());
        user.setActivated(true);
        UserDto result = userService.save(user);
        return result.getId() != null;
    }

    @Override
    @Transactional
    public void creatAdmin(SignupRequest user) throws UserAlreadyExistsException {
        if (userService.existsByUsername(user.getUsername())) {
            throw new UserAlreadyExistsException("Username already used");
        }
        if (userService.existsByEmail(user.getEmail())) {
            throw new UserAlreadyExistsException("Email already used");
        }
        user.setActivated(true);
        user.setActivatedAt(new Date());
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRole(roleService.findByName("ROLE_ADMIN"));

        userService.saveDto(user);
    }


    @Override
    public AuthResponse signin(AuthRequest authRequest) throws UserNotFoundException, DeactivatedAccountException {
        User user = getAuthUser(authRequest);

        if(!user.getActivated()){
            throw new DeactivatedAccountException("Please activate your account to be able to sign in");
        }
        if(user.getMFAEnabled() != null && user.getMFAEnabled()){
            AuthResponse response = new AuthResponse();
            response.setIsMFA(true);
            response.setMFAId(sendMFAEmail(user));
            return response;
        } else {
            return authenticate(user);
        }
    }
    @Override
    public AuthResponse MFASignin(String MFAToken) throws DataNotFoundException, TokenExpiredException {
        User user = verifTokenService.verify(VerifTokenType.MFAAUTH, MFAToken.replaceAll("^\"|\"$", ""));
        return authenticate(user);
    }

    @Override
    public Long resendCode(Long tokenId) throws DataNotFoundException {
        User user = verifTokenService.findUserByIdAndDelete(tokenId);
        return sendMFAEmail(user);
    }

    @Override
    public AuthResponse refreshToken() throws TokenExpiredException, UserNotFoundException, DataNotFoundException {
        return authenticate(utilsService.getCurrentUser());
    }



    private User getAuthUser(AuthRequest authRequest) throws UserNotFoundException {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        Optional<User> optionalUser = userRepository.findByEmailOrUsername(authRequest.getUsername(), authRequest.getUsername());
        if (optionalUser.isEmpty()) {
            throw new UserNotFoundException("Account not found");
        }
        return optionalUser.get();
    }

    private AuthResponse authenticate(User user) {
        UserDto dto = userMapper.toUserDto(user);
        JwtResponse accessToken = jwtProvider.generateJwtToken(user, false);
        JwtResponse refreshToken = jwtProvider.generateJwtToken(user, true);
        return new AuthResponse(dto, accessToken, refreshToken, user.getRole().getName(), false, null);
    }

    private Long sendMFAEmail(User user){
        VerifToken verifToken = verifTokenService.create(VerifTokenType.MFAAUTH, user);
        Map<String, String> model = new HashMap<>();
        model.put("username", user.getUsername());
        model.put("mfaCode", verifToken.getValue());
        emailService.sendMFAEmail(user.getEmail(), model);
        return verifToken.getId();
    }

}
