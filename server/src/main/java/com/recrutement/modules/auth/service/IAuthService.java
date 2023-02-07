package com.recrutement.modules.auth.service;

import com.recrutement.exceptions.*;
import com.recrutement.modules.auth.httpRequest.AuthRequest;
import com.recrutement.modules.auth.httpRequest.SignupRequest;
import com.recrutement.modules.auth.httpResponse.AuthResponse;
import com.recrutement.modules.user.User;
import com.recrutement.modules.user.dto.UserDto;
import org.springframework.transaction.annotation.Transactional;

public interface IAuthService {

    void signup(SignupRequest request) throws UserAlreadyExistsException;

    @Transactional
    void creatAdmin(SignupRequest request) throws UserAlreadyExistsException;

    AuthResponse signin(AuthRequest authRequest) throws UserNotFoundException, DeactivatedAccountException;

    AuthResponse MFASignin(String MFAToken) throws DataNotFoundException, TokenExpiredException;

    Long resendCode(Long tokenId) throws DataNotFoundException;

    AuthResponse refreshToken() throws TokenExpiredException, UserNotFoundException;
}
