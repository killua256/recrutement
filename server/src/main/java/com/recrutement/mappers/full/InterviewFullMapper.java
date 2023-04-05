package com.recrutement.mappers.full;

import com.recrutement.dtos.full.InterviewFullDTO;
import com.recrutement.entities.Interview;
import com.recrutement.modules.base.BaseMapper;
import org.mapstruct.Mapper;

@Mapper
public interface InterviewFullMapper extends BaseMapper<InterviewFullDTO, Interview> {
}