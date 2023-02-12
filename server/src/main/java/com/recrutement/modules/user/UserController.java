package com.recrutement.modules.user;

import com.recrutement.exceptions.DataNotFoundException;
import com.recrutement.modules.user.dto.UserDto;
import com.recrutement.modules.user.service.IUserService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/v1/users")
public class UserController {
    private static final Logger logger = LogManager.getLogger(UserController.class);
    @Autowired
    private IUserService userService;

    @GetMapping("/{username}")
    public ResponseEntity<?> resendCode(@PathVariable String username) {
        try{
            UserDto response = userService.findByUsername(username);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (DataNotFoundException e){
            logger.error(e.getMessage());
            return new ResponseEntity<>("Not authenticated", HttpStatus.NOT_FOUND);
        } catch (Exception e){
            logger.error(e.getMessage());
            return new ResponseEntity<>("Resend email code failed", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
