package com.recrutement.DTOs;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class JobOfferDTO {
    private String title;
    private String description;
    private String qualifications;
    private String location;
    private Double salary;
    private Long companyId;
}
