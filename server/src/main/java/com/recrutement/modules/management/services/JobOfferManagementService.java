package com.recrutement.modules.management.services;

import com.querydsl.core.BooleanBuilder;
import com.recrutement.dtos.compact.JobOfferDTO;
import com.recrutement.entities.QJobOffer;
import com.recrutement.services.CompanyService;
import com.recrutement.services.JobOfferService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;

import java.util.Set;


@Service
@RequiredArgsConstructor
public class JobOfferManagementService {

    private final JobOfferService jobOfferService;
    private final CompanyService companyService;

    public JobOfferDTO createJobOffer(JobOfferDTO jobDTO, long companyId) {
        // Check if the company existsPut
        if(!companyService.exists(companyId))
            throw new EntityNotFoundException("Company with id " + jobDTO.getCompanyId() + " Does not exist");
        jobDTO.setCompanyId(companyId);
        return jobOfferService.save(jobDTO);
    }

    public JobOfferDTO updateJobOffer(JobOfferDTO jobDTO, long companyId) {
        if(jobDTO.getCompanyId() != companyId)
            throw new AccessDeniedException("Job offer does not belong to current user");
        if(jobDTO.getId() == null || !jobOfferService.exists(jobDTO.getId()))
            throw new EntityNotFoundException("jobOfferExists with id " + jobDTO.getId() + " Does not exist");
        return jobOfferService.update(jobDTO);
    }

    public void removeJobOffer(long jobOfferId, long companyId) {
        JobOfferDTO jobOffer = jobOfferService.getOne(jobOfferId);
        if(jobOffer.getCompanyId() != companyId)
            throw new AccessDeniedException("jobOffer with id " + jobOfferId + " Does not belong to current user");

        jobOfferService.softDelete(jobOfferId);
    }

    public void openJobOffer(long jobOfferId, long companyId) {
        JobOfferDTO jobOffer = jobOfferService.getOne(jobOfferId);
        if(jobOffer.getCompanyId() != companyId)
            throw new AccessDeniedException("jobOffer with id " + jobOfferId + " Does not belong to current user");
        jobOfferService.changeJobOfferStatus(jobOfferId, true);
    }

    public void closeJobOffer(long jobOfferId, long companyId) {
        JobOfferDTO jobOffer = jobOfferService.getOne(jobOfferId);
        if(jobOffer.getCompanyId() != companyId)
            throw new AccessDeniedException("jobOffer with id " + jobOfferId + " Does not belong to current user");
        jobOfferService.changeJobOfferStatus(jobOfferId, false);
    }

    public JobOfferDTO getJobOffer(long jobOfferId) {
        return jobOfferService.getOne(jobOfferId);
    }

    public Set<JobOfferDTO> getJobOffers() {
        return jobOfferService.getAllDistinct();
    }

    public Set<JobOfferDTO> getJobOffers(boolean isOpen,long companyId) {
        BooleanBuilder where = new BooleanBuilder();
        QJobOffer JobOffer = QJobOffer.jobOffer;
        where.and(JobOffer.isOpen.eq(isOpen));
        where.and(JobOffer.company.id.eq(companyId));
        return jobOfferService.getAllDistinct(where);
    }

    public Set<JobOfferDTO> getJobOffers(boolean isOpen) {
        BooleanBuilder where = new BooleanBuilder();
        QJobOffer JobOffer = QJobOffer.jobOffer;
        where.and(JobOffer.isOpen.eq(isOpen));
        return jobOfferService.getAllDistinct(where);
    }

    public Set<JobOfferDTO> getJobOffers(long companyId) {
        BooleanBuilder where = new BooleanBuilder();
        QJobOffer JobOffer = QJobOffer.jobOffer;
        where.and(JobOffer.company.id.eq(companyId));
        return jobOfferService.getAllDistinct(where);
    }

}
