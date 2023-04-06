package com.recrutement.dtos.compact;

import com.recrutement.dtos.BaseDTO;
import com.recrutement.enums.ApplicationStatus;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ApplicationDTO extends BaseDTO {
    private Long jobOfferId;
    private ApplicationStatus status;
    private Long applicantId;
    private Long companyId;
}
