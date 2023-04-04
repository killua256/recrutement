package com.recrutement.DTOs;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ApplicantDTO extends BaseDTO {
    private String resumeUrl;
    private String skills;
    private String education;
    private Long userId;
}
