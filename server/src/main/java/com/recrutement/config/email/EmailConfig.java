package com.recrutement.config.email;

import com.recrutement.config.ExternalConfigs;
import com.recrutement.utils.email.EmailData;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;

import java.util.Properties;

@Configuration
public class EmailConfig {
    @Autowired
    private JavaMailSender javaMailSender;
    @Autowired
    private ExternalConfigs externalConfigs;


    public Boolean send(EmailData emailData, String body) {
        try {
            EmailData data = buildData(emailData);
            this.javaMailSender = getJavaMailSender(data);
            MimeMessage mail = javaMailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mail, true);
            helper.setFrom(data.getFrom());
            helper.setTo(data.getTo());
            helper.setSubject(data.getSubject());
            helper.setText("", body);
            this.javaMailSender.send(mail);
            return true;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return false;
        }
    }

    private boolean nullOrEmpty(String req) {
        return req == null || req.trim().equals("");
    }

    private EmailData buildData(EmailData emailData) {
        EmailData response = new EmailData();
        response.setFrom(nullOrEmpty(emailData.getFrom()) ? externalConfigs.getEmailUsername() : emailData.getFrom());
        response.setHost(nullOrEmpty(emailData.getHost()) ? externalConfigs.getEmailHost() : emailData.getHost());
        response.setPassowrd(nullOrEmpty(emailData.getPassowrd()) ? externalConfigs.getEmailPassword() : emailData.getPassowrd());
        response.setProtocol(nullOrEmpty(emailData.getProtocol()) ? externalConfigs.getEmailProtocol() : emailData.getProtocol().toLowerCase());
        response.setPort(emailData.getPort() == null ? externalConfigs.getEmailHostPort() : emailData.getPort());
        response.setSubject(emailData.getSubject());
        response.setTo(emailData.getTo());
        return response;
    }

    private JavaMailSender getJavaMailSender(EmailData data) {
        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
        mailSender.setHost(data.getHost());
        mailSender.setPort(data.getPort());

        mailSender.setUsername(data.getFrom());
        mailSender.setPassword(data.getPassowrd());
        mailSender.setProtocol(data.getProtocol());
        Properties props = mailSender.getJavaMailProperties();
        props.put("mail.transport.protocol", data.getProtocol());
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.startssl.enable", true);
        props.put("mail.debug", "false");
        props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        props.put("mail.smtp.socketFactory.fallback", "false");
        props.put("mail.smtp.ssl.enable", "true");
        return mailSender;
    }
}
