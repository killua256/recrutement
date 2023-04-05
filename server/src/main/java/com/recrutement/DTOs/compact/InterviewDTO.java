package com.recrutement.dtos.compact;

import com.recrutement.dtos.BaseDTO;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
public class InterviewDTO extends BaseDTO {
    private Long applicationId;
    private Date interviewDate;
    private Date interviewTime;
    private String interviewLocation;
    private String interviewerDetails;
}


