package com.recrutement.utils;

import com.recrutement.exceptions.UserNotFoundException;
import com.recrutement.services.ApplicantService;
import com.recrutement.services.CompanyService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import com.recrutement.config.ExternalConfigs;
import com.recrutement.modules.user.User;
import com.recrutement.modules.user.UserMapper;
import com.recrutement.modules.user.dto.UserDto;

import java.io.File;
import java.nio.file.Paths;

@Service
@RequiredArgsConstructor
public class UtilsService {
    private final UserMapper userMapper;
    private final ExternalConfigs externalConfigs;
    private final String UPLOAD_DIR = "recruitment";
    private final ApplicantService applicantService;
    private final CompanyService companyService;
    public User getCurrentUser() throws UserNotFoundException {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if(!authentication.isAuthenticated() || !(authentication.getPrincipal() instanceof User)){
            throw new UserNotFoundException("Cannot load current user");
        }
        return (User) authentication.getPrincipal();
    }

    public long getApplicantId() throws UserNotFoundException {
        User user = getCurrentUser();
        return applicantService.getApplicantByUserId(user.getId()).getId();
    }
    public long getCompanyId() throws UserNotFoundException {
        User user = getCurrentUser();
        return companyService.getCompanyByUserId(user.getId()).getId();
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
