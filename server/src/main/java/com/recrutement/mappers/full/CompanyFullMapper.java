package com.recrutement.mappers.full;

import com.recrutement.dtos.full.CompanyFullDTO;
import com.recrutement.entities.Company;
import com.recrutement.modules.base.BaseMapper;
import org.mapstruct.Mapper;

@Mapper
public interface CompanyFullMapper extends BaseMapper<CompanyFullDTO, Company> {
}
