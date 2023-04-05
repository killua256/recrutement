package com.recrutement.mappers;

import com.recrutement.dtos.compact.CompanyDTO;
import com.recrutement.entities.Company;
import com.recrutement.modules.base.BaseMapper;
import org.mapstruct.Mapper;

@Mapper
public interface CompanyMapper extends BaseMapper<CompanyDTO, Company> {
}
