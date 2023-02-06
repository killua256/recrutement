package com.recrutement.config.data;

import com.recrutement.config.ExternalConfigs;
import com.recrutement.exceptions.UserAlreadyExistsException;
import com.recrutement.modules.auth.httpRequest.SignupRequest;
import com.recrutement.modules.auth.service.IAuthService;
import com.recrutement.modules.role.IRoleService;
import lombok.AllArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
@AllArgsConstructor
public class DataLoader implements ApplicationRunner {

    private static final Logger logger = LogManager.getLogger(DataLoader.class);
    @Autowired
    private IRoleService roleService;
    @Autowired
    private IAuthService authService;

    @Autowired
    private ExternalConfigs externalConfigs;

    public void run(ApplicationArguments args) {
        if (!externalConfigs.isDatabaseInitialized()) {
            initData();
            logger.info("Data initialized successfully.");
            externalConfigs.setIsDatabaseInitialized(true);
        } else {
            logger.info("Data already initialized.");
        }
    }

    private void initData(){
        try {
            createRoles();
            createAdmin();
        } catch (Exception e){
            logger.error(e.getMessage());
        }
    }

    private void createRoles() {
        roleService.createByName("ROLE_ADMIN");
        roleService.createByName("ROLE_USER");
    }
    private void createAdmin() throws UserAlreadyExistsException {
        SignupRequest dto = new SignupRequest();
        dto.setFirstname("admin");
        dto.setLastname("admin");
        dto.setUsername("admin");
        dto.setEmail("admin@mail.com");
        dto.setPassword("password");
        dto.setActivated(true);
        dto.setActivatedAt(new Date());
        authService.creatAdmin(dto);
    }

}
