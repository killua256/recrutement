package com.recrutement.dtos.full;

import com.recrutement.dtos.BaseDTO;
import com.recrutement.dtos.compact.CompanyDTO;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class JobOfferFullDTO extends BaseDTO {
    private String title;
    private String description;
    private String qualifications;
    private String location;
    private Double salary;
    private CompanyDTO company;
    private boolean isOpen;
}
