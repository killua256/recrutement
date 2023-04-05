package com.recrutement.mappers.full;

import com.recrutement.dtos.full.ApplicationFullDTO;
import com.recrutement.entities.Application;
import com.recrutement.modules.base.BaseMapper;
import org.mapstruct.Mapper;

@Mapper
public interface ApplicationFullMapper extends BaseMapper<ApplicationFullDTO, Application> {
}
