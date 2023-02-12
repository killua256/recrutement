package com.recrutement.modules.user;

import com.recrutement.exceptions.DataNotFoundException;
import com.recrutement.modules.documents.Document;
import com.recrutement.modules.documents.services.IDocumentsService;
import com.recrutement.modules.user.dto.UserDto;
import com.recrutement.modules.user.service.IUserService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;


@RestController
@RequestMapping("/api/v1/users")
public class UserController {
    private static final Logger logger = LogManager.getLogger(UserController.class);
    @Autowired
    private IUserService userService;
    @Autowired
    private IDocumentsService documentsService;

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

    @PostMapping(value = "/avatar", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> changeAvatar(@RequestParam(name = "avatar") MultipartFile avatar) {
        try {
            Document avatarDoc = documentsService.upload(avatar, "users");
            UserDto response = userService.updateAvatar(avatarDoc);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            logger.error(e.getMessage());
            return new ResponseEntity<>("Changing avatar failed", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
