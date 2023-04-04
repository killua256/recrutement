package com.recrutement.repositories;

import com.recrutement.DTOs.CompanyDTO;
import com.recrutement.entities.Company;
import com.recrutement.modules.base.BaseRepository;
import org.mapstruct.Mapper;

@Mapper
public interface CompanyRepository extends BaseRepository<Company> {
}
