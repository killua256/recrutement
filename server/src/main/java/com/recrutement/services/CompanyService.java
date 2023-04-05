package com.recrutement.services;

import com.recrutement.dtos.compact.CompanyDTO;
import com.recrutement.dtos.full.CompanyFullDTO;
import com.recrutement.entities.Company;
import com.recrutement.mappers.compact.CompanyMapper;
import com.recrutement.mappers.full.CompanyFullMapper;
import com.recrutement.modules.base.BaseMapper;
import com.recrutement.modules.base.BaseRepository;
import com.recrutement.modules.base.BaseService;
import com.recrutement.repositories.CompanyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CompanyService extends BaseService<Company, CompanyDTO, CompanyFullDTO> {

    private final CompanyRepository companyRepository;
    private final CompanyMapper companyMapper;
    private final CompanyFullMapper companyFullMapper;


    @Override
    protected Class<Company> getType() {
        return Company.class;
    }

    @Override
    protected BaseRepository<Company> getRepository() {
        return companyRepository;
    }

    @Override
    protected BaseMapper<CompanyDTO, Company> getMapper() {
        return companyMapper;
    }

    @Override
    protected BaseMapper<CompanyFullDTO, Company> getFullMapper() {
        return companyFullMapper;
    }


    public CompanyDTO getCompanyByUserId(long userId){
        return companyMapper.toDto(companyRepository.getByUserId(userId));
    }
}
