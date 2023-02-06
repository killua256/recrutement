package com.recrutement.modules.user;

import com.recrutement.modules.user.service.IUserService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/v1/users")
@Validated
public class UserController {
    private static final Logger logger = LogManager.getLogger(UserController.class);
    @Autowired
    private IUserService userService;


}
