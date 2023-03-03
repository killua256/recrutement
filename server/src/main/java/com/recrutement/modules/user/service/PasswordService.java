package com.recrutement.modules.user.service;

import com.recrutement.exceptions.InvalidPassword;
import com.recrutement.exceptions.UserNotFoundException;
import com.recrutement.modules.user.User;
import com.recrutement.modules.user.UserMapper;
import com.recrutement.modules.user.UserRepository;
import com.recrutement.modules.user.dto.ChangePasswordReq;
import com.recrutement.utils.PasswordHandler;
import com.recrutement.utils.UtilsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class PasswordService implements IPasswordService{
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private UtilsService utilsService;

    @Override
    public Boolean changePassword(ChangePasswordReq request) throws InvalidPassword, UserNotFoundException {
        User user = utilsService.getCurrentUser();
        if (!passwordEncoder.matches(request.getCurrentPassword(), user.getPassword())) {
            throw new InvalidPassword("Invalid password!");
        }
        if(!PasswordHandler.isValidPassword(request.getNewPassword())){
            throw new InvalidPassword("Password must have at least 8 characters, 1 uppercase, 1 lowercase character, 1 number and 1 special character!");
        }
        if (!request.getNewPassword().equals(request.getConfirmPassword())) {
            throw new InvalidPassword("New password and confirmation password must match!");
        }
        user.setPassword(passwordEncoder.encode(request.getNewPassword()));
        userRepository.save(user);
        return true;
    }
    @Autowired
    private PasswordEncoder passwordEncoder;
}
