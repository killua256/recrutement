package com.recrutement.mappers.compact;

import com.recrutement.dtos.compact.ApplicationDTO;
import com.recrutement.entities.Application;
import com.recrutement.modules.base.BaseMapper;

import org.mapstruct.Mapper;

@Mapper
public interface ApplicationMapper extends BaseMapper<ApplicationDTO, Application> {
}