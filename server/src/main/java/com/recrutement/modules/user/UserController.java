package com.recrutement.modules.user;

import com.recrutement.exceptions.DataNotFoundException;
import com.recrutement.exceptions.UserNotFoundException;
import com.recrutement.modules.documents.Document;
import com.recrutement.modules.documents.services.IDocumentsService;
import com.recrutement.modules.user.dto.UserDto;
import com.recrutement.modules.user.service.IUserService;
import com.recrutement.utils.UtilsService;
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
    @Autowired
    private UtilsService utilsService;

    @GetMapping("/{username}")
    public ResponseEntity<?> findByUsername(@PathVariable String username) {
        try{
            UserDto response = userService.findByUsername(username);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (DataNotFoundException e){
            logger.error(e.getMessage());
            return new ResponseEntity<>("User data not found", HttpStatus.NOT_FOUND);
        } catch (Exception e){
            logger.error(e.getMessage());
            return new ResponseEntity<>("Loading user data failed", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<?> update(
            @PathVariable Long id,
            @RequestBody UserDto user
    ) {
        try {
            UserDto response = userService.update(id, user);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (UserNotFoundException e) {
            logger.error(e.getMessage());
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            logger.error(e.getMessage());
            return new ResponseEntity<>("Updating user data failed", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping(value = "/avatar", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> changeAvatar(@RequestParam(name = "avatar") MultipartFile avatar) {
        try {
            Document avatarDoc = documentsService.upload(avatar, "users");
            Document oldDoc = utilsService.getCurrentUser().getAvatar();
            UserDto response = userService.updateAvatar(avatarDoc);
            documentsService.delete(oldDoc);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (UserNotFoundException e) {
            logger.error(e.getMessage());
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            logger.error(e.getMessage());
            return new ResponseEntity<>("Changing avatar failed", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping(value = "/cover", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> changeCover(@RequestParam(name = "cover") MultipartFile cover) {
        try {
            Document coverDoc = documentsService.upload(cover, "users");
            Document oldDoc = utilsService.getCurrentUser().getCover();
            UserDto response = userService.updateCover(coverDoc);
            documentsService.delete(oldDoc);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (UserNotFoundException e) {
            logger.error(e.getMessage());
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            logger.error(e.getMessage());
            return new ResponseEntity<>("Changing cover failed", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
