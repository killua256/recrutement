package com.recrutement.modules.jobOfferManagement;

import com.querydsl.core.BooleanBuilder;
import com.recrutement.DTOs.ApplicationDTO;
import com.recrutement.DTOs.JobOfferDTO;
import com.recrutement.entities.QApplication;
import com.recrutement.modules.jobOfferManagement.exceptions.JobOfferClosedException;
import com.recrutement.services.ApplicantService;
import com.recrutement.services.ApplicationService;
import com.recrutement.services.JobOfferService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
@RequiredArgsConstructor
public class ApplicationManagementService {

    private final JobOfferService jobOfferService;
    private final ApplicationService applicationService;
    private final ApplicantService applicantService;

    public ApplicationDTO applyForJob(long jobOfferId, ApplicationDTO applicationDTO) throws JobOfferClosedException {
        JobOfferDTO jobOffer = jobOfferService.getOne(jobOfferId);
        if(!jobOffer.isOpen())
            throw new JobOfferClosedException("Job offer with id " + jobOfferId +" is closed");
        if(applicantService.exists(applicationDTO.getApplicantId()))
            throw new EntityNotFoundException("Applicant with id " + applicationDTO.getApplicantId() + " Does not exist");

        return applicationService.save(applicationDTO);
    }

    public void deleteApplicationFromJob(long jobOfferId, long applicantId) throws JobOfferClosedException {
        JobOfferDTO jobOffer = jobOfferService.getOne(jobOfferId);
        if(!jobOffer.isOpen())
            throw new JobOfferClosedException("Job offer with id " + jobOfferId +" is closed");
        if(applicantService.exists(applicantId))
            throw new EntityNotFoundException("Applicant with id " + applicantId + " Does not exist");

        applicationService.deleteByApplicantId(applicantId);
    }

    public Set<ApplicationDTO> getApplicationsByJobOfferId(long jobOfferId) {
        if(jobOfferService.exists(jobOfferId))
            throw new EntityNotFoundException("Job offer with id " + jobOfferId +" Does not exist");
        BooleanBuilder where = new BooleanBuilder();
        QApplication qApplication = QApplication.application;
        where.and(qApplication.jobOffer.id.eq(jobOfferId));
        return applicationService.getAllDistinct(where);
    }
}
