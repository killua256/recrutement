package com.recrutement.mappers;

import com.recrutement.DTOs.InterviewDTO;
import com.recrutement.entities.Interview;
import com.recrutement.modules.base.BaseMapper;
import org.mapstruct.Mapper;

@Mapper
public interface InterviewMapper extends BaseMapper<InterviewDTO, Interview> {
}