package com.recrutement.modules.user.dto;

import com.recrutement.modules.base.BaseDto;
import com.recrutement.modules.role.RoleDto;
import lombok.*;

import java.util.HashSet;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserMinDto extends BaseDto {

    private String firstname;
    private String lastname;
    private String username;
    private String displayName;
    private String email;
    private String image;
    private RoleDto role;
}
