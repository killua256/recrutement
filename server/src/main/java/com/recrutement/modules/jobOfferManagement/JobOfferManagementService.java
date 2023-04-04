package com.recrutement.modules.jobOfferManagement;

import com.recrutement.DTOs.ApplicationDTO;
import com.recrutement.DTOs.JobOfferDTO;
import com.recrutement.services.ApplicantService;
import com.recrutement.services.ApplicationService;
import com.recrutement.services.CompanyService;
import com.recrutement.services.JobOfferService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class JobOfferManagementService {

    private final JobOfferService jobOfferService;
    private final CompanyService companyService;
    private final ApplicationService applicationService;
    private final ApplicantService applicantService;


    public JobOfferDTO createJobOffer(JobOfferDTO jobDTO) {
        // Check if the company exists
        if(jobDTO.getCompanyId() == null || !companyService.exists(jobDTO.getCompanyId()))
            throw new NoSuchElementException("Company with id " + jobDTO.getCompanyId() + " Does not exist");
        return jobOfferService.save(jobDTO);
    }

    public JobOfferDTO updateJobOffer(JobOfferDTO jobDTO) {
        if(jobDTO.getId() == null || !jobOfferService.exists(jobDTO.getId()))
            throw new NoSuchElementException("jobOfferExists with id " + jobDTO.getId() + " Does not exist");
        return jobOfferService.update(jobDTO);
    }

    public ApplicationDTO applyForJob(Long jobOfferId, ApplicationDTO applicationDTO) {
        if(jobOfferService.exists(jobOfferId))
            throw new NoSuchElementException("Job offer with id " +jobOfferId+" Does not exist");
        if(applicantService.exists(applicationDTO.getApplicantId()))
            throw new NoSuchElementException("Applicant with id " +applicationDTO.getApplicantId()+" Does not exist");

        return applicationService.save(applicationDTO);
    }

    public Set<ApplicationDTO> getApplicationsByJobOfferId(Long jobOfferId) {
        if(jobOfferService.exists(jobOfferId))
            throw new NoSuchElementException("Job offer with id " +jobOfferId+" Does not exist");

        return applicationService.getAllDistinct();
    }

}
