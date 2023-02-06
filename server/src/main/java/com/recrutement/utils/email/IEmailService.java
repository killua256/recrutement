package com.recrutement.utils.email;

import com.recrutement.modules.user.dto.UserDto;

import java.util.Map;

public interface IEmailService {

    Boolean sendActivationEmail(String email, Map<String, String> model);
    void sendOtpEmail(String email, Map<String, String> model);
    Boolean sendForgotPasswordEmail(String email, Map<String, String> model);
}
