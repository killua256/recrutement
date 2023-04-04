package com.recrutement.modules.jobOfferManagement;

import com.querydsl.core.BooleanBuilder;
import com.recrutement.DTOs.JobOfferDTO;
import com.recrutement.entities.QJobOffer;
import com.recrutement.services.CompanyService;
import com.recrutement.services.JobOfferService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Set;


@Service
@RequiredArgsConstructor
public class JobOfferManagementService {

    private final JobOfferService jobOfferService;
    private final CompanyService companyService;

    public JobOfferDTO createJobOffer(JobOfferDTO jobDTO) {
        // Check if the company exists
        if(jobDTO.getCompanyId() == null || !companyService.exists(jobDTO.getCompanyId()))
            throw new EntityNotFoundException("Company with id " + jobDTO.getCompanyId() + " Does not exist");
        return jobOfferService.save(jobDTO);
    }

    public JobOfferDTO updateJobOffer(JobOfferDTO jobDTO) {
        if(jobDTO.getId() == null || !jobOfferService.exists(jobDTO.getId()))
            throw new EntityNotFoundException("jobOfferExists with id " + jobDTO.getId() + " Does not exist");
        return jobOfferService.update(jobDTO);
    }

    public void removeJobOffer(long jobOfferId) {
        if(!jobOfferService.exists(jobOfferId))
            throw new EntityNotFoundException("jobOfferExists with id " + jobOfferId + " Does not exist");

        jobOfferService.softDelete(jobOfferId);
    }

    public void openJobOffer(long jobOfferId) {
        if(!jobOfferService.exists(jobOfferId))
            throw new EntityNotFoundException("jobOfferExists with id " + jobOfferId + " Does not exist");
        jobOfferService.changeJobOfferStatus(jobOfferId, true);
    }

    public void closeJobOffer(long jobOfferId) {
        if(!jobOfferService.exists(jobOfferId))
            throw new EntityNotFoundException("jobOfferExists with id " + jobOfferId + " Does not exist");
        jobOfferService.changeJobOfferStatus(jobOfferId, false);
    }

    public Set<JobOfferDTO> getJobOffers() {
        return jobOfferService.getAllDistinct();
    }

    public Set<JobOfferDTO> getJobOffers(boolean isOpen) {
        BooleanBuilder where = new BooleanBuilder();
        QJobOffer JobOffer = QJobOffer.jobOffer;
        where.and(JobOffer.isOpen.eq(isOpen));
        return jobOfferService.getAllDistinct(where);
    }

    public Set<JobOfferDTO> getJobOffersByCompany(long companyId) {
        BooleanBuilder where = new BooleanBuilder();
        QJobOffer JobOffer = QJobOffer.jobOffer;
        where.and(JobOffer.company.id.eq(companyId));
        return jobOfferService.getAllDistinct(where);
    }

}
