package com.recrutement.modules.auth.httpRequest;

import com.recrutement.modules.user.dto.UserDto;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
public class SignupRequest extends UserDto {
    private String password;
    private Boolean activated;
    private Date activatedAt;
}
