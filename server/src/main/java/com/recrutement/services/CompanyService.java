package com.recrutement.services;

import com.recrutement.DTOs.ApplicantDTO;
import com.recrutement.DTOs.CompanyDTO;
import com.recrutement.entities.Company;
import com.recrutement.mappers.CompanyMapper;
import com.recrutement.modules.base.BaseMapper;
import com.recrutement.modules.base.BaseRepository;
import com.recrutement.modules.base.BaseService;
import com.recrutement.repositories.CompanyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CompanyService extends BaseService<Company, CompanyDTO> {

    private final CompanyRepository companyRepository;
    private final CompanyMapper companyMapper;


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


    public CompanyDTO getCompanyByUserId(long userId){
        return companyMapper.toDto(companyRepository.getByUserId(userId));
    }
}
