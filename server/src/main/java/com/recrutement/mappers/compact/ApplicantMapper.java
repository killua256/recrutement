package com.recrutement.mappers.compact;

import com.recrutement.dtos.compact.ApplicantDTO;
import com.recrutement.entities.Applicant;
import com.recrutement.modules.base.BaseMapper;
import org.mapstruct.Mapper;

@Mapper
public interface ApplicantMapper extends BaseMapper<ApplicantDTO, Applicant> {
}
