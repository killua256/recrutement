package com.recrutement.DTOs;

import jakarta.persistence.Column;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
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


