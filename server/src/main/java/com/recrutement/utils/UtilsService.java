package com.recrutement.utils;

import com.recrutement.exceptions.DataNotFoundException;
import com.recrutement.exceptions.UserNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import com.recrutement.config.ExternalConfigs;
import com.recrutement.modules.user.User;
import com.recrutement.modules.user.UserMapper;
import com.recrutement.modules.user.UserRepository;
import com.recrutement.modules.user.dto.UserDto;

import java.io.File;
import java.nio.file.Paths;

@Service
public class UtilsService {
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private ExternalConfigs externalConfigs;
    private final String UPLOAD_DIR = "recruitment";

    public User getCurrentUser() throws UserNotFoundException {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if(!authentication.isAuthenticated() || !(authentication.getPrincipal() instanceof User)){
            throw new UserNotFoundException("Cannot load current user");
        }
        return (User) authentication.getPrincipal();
    }

    public UserDto getCurrentUserDto() throws UserNotFoundException {
        return userMapper.toUserDto(getCurrentUser());
    }

    public String getTempDir(String res) {
        if (externalConfigs.isAppEnvDev()) {
            return System.getProperty("java.io.tmpdir") +
                    UPLOAD_DIR + File.separator +
                    res + File.separator;
        }
        return Paths.get(
                        externalConfigs.getRessourceFolderPath() +
                                UPLOAD_DIR + File.separator + res + File.separator
                )
                .toAbsolutePath().normalize().toString();
    }
}
