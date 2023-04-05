package com.recrutement.dtos.full;

import com.recrutement.dtos.BaseDTO;
import com.recrutement.modules.user.dto.UserDto;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ApplicantFullDTO extends BaseDTO {
    private String resumeUrl;
    private String skills;
    private String education;
    private UserDto user;
}
