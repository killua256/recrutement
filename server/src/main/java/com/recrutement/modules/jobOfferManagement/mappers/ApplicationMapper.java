package com.recrutement.modules.jobOfferManagement.mappers;

import com.recrutement.DTOs.ApplicationDTO;
import com.recrutement.entities.Application;
import com.recrutement.modules.base.BaseMapper;

import org.mapstruct.Mapper;

@Mapper
public interface ApplicationMapper extends BaseMapper<ApplicationDTO, Application> {
}
