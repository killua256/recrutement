package com.recrutement.mappers;

import com.recrutement.dtos.compact.JobOfferDTO;
import com.recrutement.entities.JobOffer;
import com.recrutement.modules.base.BaseMapper;
import org.mapstruct.Mapper;

@Mapper
public interface JobOfferMapper extends BaseMapper<JobOfferDTO, JobOffer> {
}
