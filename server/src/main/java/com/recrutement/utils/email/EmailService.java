package com.recrutement.utils.email;

import com.recrutement.config.email.EmailConfig;
import freemarker.cache.ClassTemplateLoader;
import freemarker.cache.FileTemplateLoader;
import freemarker.cache.MultiTemplateLoader;
import freemarker.cache.TemplateLoader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import freemarker.template.Configuration;

import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;
import com.recrutement.utils.UtilsService;
import com.recrutement.config.ExternalConfigs;

import java.io.File;
import java.text.DecimalFormat;
import java.util.Map;


@Service
public class EmailService implements IEmailService {

    private final Logger logger = LoggerFactory.getLogger(EmailService.class);
    @Autowired
    public JavaMailSender emailSender;
    @Autowired
    Configuration fmConfiguration;
    @Autowired
    private EmailConfig emailConfig;
    @Autowired
    private UtilsService utilsService;
    @Autowired
    private ExternalConfigs externalConfigs;

    @Override
    @Async
    public void sendActivationEmail(String email, Map<String, String> model) {
        try {
            System.out.println("sendActivationEmail......................");
            System.out.println("email--> " + email);
            EmailData emailData = new EmailData();
            emailData.setSubject("Activate Your Account");
            emailData.setTo(email);
            model.put("expiration", formatExpirationValue(
                    externalConfigs.getAccountActivationTokenExpiration()
            ));

            String body = geContentFromTemplate(model, "account-activation.ftl");
            emailConfig.send(emailData, body);
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
    }

    @Override
    @Async
    public void sendMFAEmail(String email, Map<String, String> model) {
        try {
            System.out.println("send mfa email......................");
            System.out.println("email--> " + email);
            EmailData emailData = new EmailData();
            emailData.setSubject("SignIn code");
            emailData.setTo(email);
            model.put("expiration", formatExpirationValue(
                    externalConfigs.getMFATokenExpiration()
            ));

            String body = geContentFromTemplate(model, "auth-mfa.ftl");
            emailConfig.send(emailData, body);
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
    }

    @Override
    public Boolean sendForgotPasswordEmail(String email, Map<String, String> model) {
        try {
            System.out.println("sendForgotPasswordEmail------------------- ");
            EmailData emailData = new EmailData();
            emailData.setSubject("Reset Password");
            emailData.setTo(email);
            model.put("expiration", formatExpirationValue(
                    externalConfigs.getForgotPasswordTokenExpiration()
            ));

            String body = geContentFromTemplate(model, "forgot-password.ftl");
            emailConfig.send(emailData, body);
            System.out.println("success----- ");
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }

    }

    private String geContentFromTemplate(Map<String, String> model, String template) {
        StringBuilder content = new StringBuilder();
        try {
            ClassTemplateLoader ctl = new ClassTemplateLoader(getClass(), "/templates/");
            //FileTemplateLoader ftl1 = new FileTemplateLoader(new File(utilsService.getTempDir("templates")));
            TemplateLoader[] loaders = new TemplateLoader[]{ctl};
            MultiTemplateLoader mtl = new MultiTemplateLoader(loaders);
            fmConfiguration.setTemplateLoader(mtl);
            content.append(
                    FreeMarkerTemplateUtils.processTemplateIntoString(
                            fmConfiguration.getTemplate(template),
                            model
                    )
            );
        } catch (Exception e) {
            e.printStackTrace();
        }
        return content.toString();
    }

    private String formatExpirationValue(Integer expiration) {
        if (expiration < 60) {
            return expiration + " minutes";
        }
        if (expiration % 60 == 0) {
            return (expiration / 60) + " hours";
        }
        DecimalFormat formatter = new DecimalFormat("#0.0");
        double d = expiration / (double) 60;
        return formatter.format(d) + " hours";
    }
}
