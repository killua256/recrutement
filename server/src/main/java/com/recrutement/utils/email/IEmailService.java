package com.recrutement.utils.email;

import java.util.Map;

public interface IEmailService {

    Boolean sendActivationEmail(String email, Map<String, String> model);
    void sendMFAEmail(String email, Map<String, String> model);
    Boolean sendForgotPasswordEmail(String email, Map<String, String> model);
}
