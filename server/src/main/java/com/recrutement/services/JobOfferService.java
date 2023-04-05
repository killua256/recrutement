package com.recrutement.services;

import com.recrutement.dtos.compact.JobOfferDTO;
import com.recrutement.dtos.full.JobOfferFullDTO;
import com.recrutement.entities.JobOffer;
import com.recrutement.mappers.compact.JobOfferMapper;
import com.recrutement.mappers.full.JobOfferFullMapper;
import com.recrutement.modules.base.BaseMapper;
import com.recrutement.modules.base.BaseRepository;
import com.recrutement.modules.base.BaseService;
import com.recrutement.repositories.JobOfferRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class JobOfferService extends BaseService<JobOffer, JobOfferDTO, JobOfferFullDTO> {

    private final JobOfferRepository jobOfferRepository;
    private final JobOfferMapper jobOfferMapper;
    private final JobOfferFullMapper jobOfferFullMapper;

    @Override
    protected Class<JobOffer> getType() {
        return JobOffer.class;
    }

    @Override
    protected BaseRepository<JobOffer> getRepository() {
        return jobOfferRepository;
    }

    @Override
    protected BaseMapper<JobOfferDTO, JobOffer> getMapper() {
        return jobOfferMapper;
    }

    @Override
    protected BaseMapper<JobOfferFullDTO, JobOffer> getFullMapper() {
        return jobOfferFullMapper;
    }

    public void changeJobOfferStatus(long id, boolean status){
        jobOfferRepository.updateIsOpen(id, status);
    }
}
