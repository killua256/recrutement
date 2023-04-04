package com.recrutement.DTOs;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class CompanyDTO extends BaseDTO {
    private String name;
    private String description;
    private String address;
    private Long userId;
}
