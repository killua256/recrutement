package com.recrutement.dtos.full;

import com.recrutement.dtos.BaseDTO;
import com.recrutement.dtos.compact.ApplicantDTO;
import com.recrutement.enums.ApplicationStatus;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ApplicationFullDTO extends BaseDTO {
    private Long jobOfferId;
    private ApplicationStatus status;
    private ApplicantDTO applicant;
}
