package com.recrutement.mappers.full;

import com.recrutement.dtos.full.ApplicantFullDTO;
import com.recrutement.entities.Applicant;
import com.recrutement.modules.base.BaseMapper;
import org.mapstruct.Mapper;

@Mapper
public interface ApplicantFullMapper extends BaseMapper<ApplicantFullDTO, Applicant> {
}
