package com.recrutement.modules.joboffer.services;

import com.querydsl.core.BooleanBuilder;
import com.recrutement.dtos.compact.ApplicationDTO;
import com.recrutement.dtos.compact.JobOfferDTO;
import com.recrutement.entities.QApplication;
import com.recrutement.enums.ApplicationStatus;
import com.recrutement.modules.joboffer.exceptions.AlreadyAppliedToJobOfferException;
import com.recrutement.modules.joboffer.exceptions.JobOfferClosedException;
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

    public ApplicationDTO applyForJob(long jobOfferId, long applicantId) throws JobOfferClosedException, AlreadyAppliedToJobOfferException {
        JobOfferDTO jobOffer = jobOfferService.getOne(jobOfferId);
        if(!jobOffer.isOpen())
            throw new JobOfferClosedException("Job offer with id " + jobOfferId +" is closed");
        if(applicantService.exists(applicantId))
            throw new EntityNotFoundException("Applicant with id " + applicantId + " Does not exist");
        if (applicationService.exists(jobOfferId, applicantId))
            throw new AlreadyAppliedToJobOfferException();
        ApplicationDTO applicationDTO = new ApplicationDTO();
        applicationDTO.setApplicantId(applicantId);
        applicationDTO.setJobOfferId(jobOfferId);
        applicationDTO.setStatus(ApplicationStatus.PENDING);
        return applicationService.save(applicationDTO);
    }

    public void deleteApplicationFromJob(long jobOfferId, long applicantId) throws JobOfferClosedException {
        JobOfferDTO jobOffer = jobOfferService.getOne(jobOfferId);
        if(!jobOffer.isOpen())
            throw new JobOfferClosedException("Job offer with id " + jobOfferId +" is closed");
        if(applicantService.exists(applicantId))
            throw new EntityNotFoundException("Applicant with id " + applicantId + " Does not exist");

        applicationService.deleteApplicationFromJob(applicantId, jobOfferId);
    }

    public Set<ApplicationDTO> getApplicationsByJobOfferId(long jobOfferId) {
        if(jobOfferService.exists(jobOfferId))
            throw new EntityNotFoundException("Job offer with id " + jobOfferId +" Does not exist");
        BooleanBuilder where = new BooleanBuilder();
        QApplication qApplication = QApplication.application;
        where.and(qApplication.jobOffer.id.eq(jobOfferId));
        return applicationService.getAllDistinct(where);
    }

    public Set<ApplicationDTO> getApplications(long applicantId) {
        BooleanBuilder where = new BooleanBuilder();
        QApplication qApplication = QApplication.application;
        where.and(qApplication.id.eq(applicantId));
        return applicationService.getAllDistinct(where);
    }
}
