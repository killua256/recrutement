package com.recrutement.dtos.full;

import com.recrutement.dtos.BaseDTO;
import com.recrutement.modules.user.dto.UserDto;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class CompanyFullDTO extends BaseDTO {
    private String name;
    private String description;
    private String address;
    private UserDto user;
}
