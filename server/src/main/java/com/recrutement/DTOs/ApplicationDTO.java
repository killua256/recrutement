package com.recrutement.DTOs;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ApplicationDTO extends BaseDTO {
    private Long userId;
    private Long jobOfferId;
    private String status;
    private Long applicantId;
}
