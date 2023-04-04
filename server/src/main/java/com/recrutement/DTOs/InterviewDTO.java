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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "application_id", referencedColumnName = "id")
    private ApplicationDTO application;

    @Column(name = "interview_date")
    private Date interviewDate;

    @Column(name = "interview_time")
    private Date interviewTime;

    @Column(name = "interview_location")
    private String interviewLocation;

    @Column(name = "interviewer_details", columnDefinition = "TEXT")
    private String interviewerDetails;

}


