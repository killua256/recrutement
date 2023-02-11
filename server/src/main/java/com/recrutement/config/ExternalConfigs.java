package com.recrutement.config;

import java.io.File;
import java.io.IOException;

import jakarta.annotation.PostConstruct;
import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;
import org.apache.commons.configuration.reloading.FileChangedReloadingStrategy;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

@Component
public class ExternalConfigs {

    private static final Logger logger = LogManager.getLogger(ExternalConfigs.class);
    private File externalConfigPath;
    private PropertiesConfiguration configuration;

    @Value("${spring.profiles.active}")
    private String activeProfile;

    @Value("${external.config.path}")
    private String configPath;

    @PostConstruct
    private void init() {
        try {
            if (activeProfile.equals("prod")) {
                ClassPathResource resource = new ClassPathResource("external-config.properties");
                externalConfigPath = resource.getFile();
            } else {
                externalConfigPath = new File(configPath + "external-config-dev.properties");
            }
            configuration = new PropertiesConfiguration(externalConfigPath);

            FileChangedReloadingStrategy fileChangedReloadingStrategy = new FileChangedReloadingStrategy();

            fileChangedReloadingStrategy.setRefreshDelay(5 * 1000 * 60 * 12);
            configuration.setReloadingStrategy(fileChangedReloadingStrategy);

            encryptExternalProperties();
        } catch (ConfigurationException e) {
            logger.error("Enable to parse the config file");
            e.printStackTrace();

        } catch (IOException e) {
            logger.error("Failed to application config file");
        }
    }

    private void encryptExternalProperties() {
        try {
            configuration.save();
        } catch (ConfigurationException e) {
            logger.error("Encrypt properties value in the config file failed");
            e.printStackTrace();
        }
    }

    public boolean isDatabaseInitialized() {
        return configuration.getBoolean("db_initialized");
    }

    public void setIsDatabaseInitialized(boolean isInitialized) {
        configuration.setProperty("db_initialized", isInitialized);
        try {
            configuration.save();
        } catch (ConfigurationException e) {
            logger.error("Updating \'is_initialized\' value in the config file failed");
            e.printStackTrace();
        }
    }

    public String getRessourceFolderPath() {
        return externalConfigPath.getParentFile().getAbsolutePath() + File.separator;
    }

    public String getAppEnv() {
        return configuration.getString("app.env");
    }

    public Boolean isAppEnvDev() {
        return getAppEnv().equalsIgnoreCase("dev");
    }

    public Boolean isAppEnvProd() {
        return getAppEnv().equalsIgnoreCase("prod");
    }

    public String getClientUrl() {
        return configuration.getString("client.url");
    }

    public String getJwtSecret() {
        return configuration.getString("security.jwt.secret");
    }

    public Integer getJwtAccessTokenExpiration() {
        return configuration.getInt("security.jwt.access.expiration");
    }

    public Integer getJwtRefreshTokenExpiration() {
        return configuration.getInt("security.jwt.refresh.expiration");
    }

    public String getEmailHost() {
        return configuration.getString("spring.mail.host");
    }

    public Integer getEmailHostPort() {
        return configuration.getInt("spring.mail.host.port");
    }

    public String getEmailUsername() {
        return configuration.getString("spring.mail.username");
    }

    public String getEmailSender() {
        return configuration.getString("spring.mail.sender");
    }

    public String getEmailPassword() {
        return configuration.getString("spring.mail.password");
    }

    public String getEmailProtocol() {
        return configuration.getString("spring.mail.transport.protocol");
    }
    public Integer getMFATokenExpiration(){
        return configuration.getInt("email.token.mfa");
    }
    public Integer getAccountActivationTokenExpiration(){
        return configuration.getInt("email.token.account.activation");
    }
    public Integer getForgotPasswordTokenExpiration(){
        return configuration.getInt("email.token.forgot.password");
    }
}
