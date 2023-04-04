package com.recrutement.services;

import com.recrutement.DTOs.JobOfferDTO;
import com.recrutement.entities.JobOffer;
import com.recrutement.mappers.JobOfferMapper;
import com.recrutement.modules.base.BaseMapper;
import com.recrutement.modules.base.BaseRepository;
import com.recrutement.modules.base.BaseService;
import com.recrutement.repositories.JobOfferRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class JobOfferService extends BaseService<JobOffer, JobOfferDTO> {

    private final JobOfferRepository jobOfferRepository;
    private final JobOfferMapper jobOfferMapper;


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
}
