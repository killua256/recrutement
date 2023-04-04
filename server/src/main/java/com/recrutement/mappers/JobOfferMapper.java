package com.recrutement.mappers;

import com.recrutement.DTOs.JobOfferDTO;
import com.recrutement.entities.JobOffer;
import com.recrutement.modules.base.BaseMapper;
import org.mapstruct.Mapper;

@Mapper
public interface JobOfferMapper extends BaseMapper<JobOfferDTO, JobOffer> {
}
