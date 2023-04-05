package com.recrutement.mappers.full;

import com.recrutement.dtos.full.JobOfferFullDTO;
import com.recrutement.entities.JobOffer;
import com.recrutement.modules.base.BaseMapper;
import org.mapstruct.Mapper;

@Mapper
public interface JobOfferFullMapper extends BaseMapper<JobOfferFullDTO, JobOffer> {
}
