package com.recrutement.modules.user.service;

import com.recrutement.exceptions.InvalidPassword;
import com.recrutement.exceptions.UserNotFoundException;
import com.recrutement.modules.user.dto.ChangePasswordReq;

public interface IPasswordService {
    Boolean changePassword(ChangePasswordReq request) throws InvalidPassword, UserNotFoundException;
}
