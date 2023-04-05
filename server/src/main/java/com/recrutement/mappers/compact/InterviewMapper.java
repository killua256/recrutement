package com.recrutement.mappers.compact;

import com.recrutement.dtos.compact.InterviewDTO;
import com.recrutement.entities.Interview;
import com.recrutement.modules.base.BaseMapper;
import org.mapstruct.Mapper;

@Mapper
public interface InterviewMapper extends BaseMapper<InterviewDTO, Interview> {
}