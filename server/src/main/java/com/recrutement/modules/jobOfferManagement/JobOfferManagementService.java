package com.recrutement.modules.jobOfferManagement;

import com.recrutement.DTOs.ApplicationDTO;
import com.recrutement.DTOs.JobOfferDTO;
import com.recrutement.entities.Application;
import com.recrutement.entities.Company;
import com.recrutement.entities.JobOffer;
import com.recrutement.modules.jobOfferManagement.mappers.ApplicationMapper;
import com.recrutement.modules.jobOfferManagement.mappers.JobOfferMapper;
import com.recrutement.repositories.ApplicationRepository;
import com.recrutement.repositories.CompanyRepository;
import com.recrutement.repositories.JobOfferRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class JobOfferManagementService {

    private final JobOfferRepository jobOfferRepository;
    private final CompanyRepository companyRepository;
    private final ApplicationRepository applicationRepository;
    private final ApplicationMapper applicationMapper;
    private final JobOfferMapper jobOfferMapper;


    public JobOffer createJobOffer(JobOfferDTO jobDTO) {
        // Check if the company exists
        Company company = companyRepository.findById(jobDTO.getCompanyId())
                .orElseThrow(() -> new NullPointerException("Company not found"));

        JobOffer jobOffer = jobOfferMapper.toEntity(jobDTO);
        jobOffer.setCompany(company);

        return jobOfferRepository.save(jobOffer);
    }

    public JobOffer updateJobOffer(Long jobOfferId, JobOfferDTO jobDTO) {
        JobOffer job = jobOfferRepository.findById(jobOfferId)
                .orElseThrow(() -> new NullPointerException("Job offer not found"));

        Company company = companyRepository.findById(jobDTO.getCompanyId())
                .orElseThrow(() -> new NullPointerException("Company not found"));

        job = jobOfferMapper.toEntity(jobDTO);
        job.setCompany(company);

        return jobOfferRepository.save(job);
    }

    public void deleteJobOffer(Long jobOfferId) {
        jobOfferRepository.deleteById(jobOfferId);
    }

    public List<JobOffer> getAllJobOffers() {
        return jobOfferRepository.findAll();
    }

    public JobOffer getJobOfferById(Long jobOfferId) {
        return jobOfferRepository.findById(jobOfferId)
                .orElseThrow(() -> new NullPointerException("Job offer not found"));
    }

    public Application applyForJob(Long jobOfferId, ApplicationDTO applicationDTO) {
        JobOffer jobOffer = jobOfferRepository.findById(jobOfferId)
                .orElseThrow(() -> new NullPointerException("Job offer not found"));

        Application application = applicationMapper.toEntity(applicationDTO);
        application.setJobOffer(jobOffer);

        return applicationRepository.save(application);
    }

    public Set<Application> getApplicationsByjobOfferId(Long jobOfferId) {
        jobOfferRepository.findById(jobOfferId)
                .orElseThrow(() -> new NullPointerException("Job offer not found"));

        return applicationRepository.findByJobOfferId(jobOfferId);
    }
}
