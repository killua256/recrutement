package com.recrutement.services;

import com.recrutement.DTOs.ApplicantDTO;
import com.recrutement.entities.Applicant;
import com.recrutement.mappers.ApplicantMapper;
import com.recrutement.modules.base.BaseMapper;
import com.recrutement.modules.base.BaseRepository;
import com.recrutement.modules.base.BaseService;
import com.recrutement.repositories.ApplicantRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ApplicantService extends BaseService<Applicant, ApplicantDTO> {

    private final ApplicantRepository applicantRepository;
    private final ApplicantMapper applicantMapper;

    @Override
    protected Class<Applicant> getType() {
        return Applicant.class;
    }

    @Override
    protected BaseRepository<Applicant> getRepository() {
        return applicantRepository;
    }

    @Override
    protected BaseMapper<ApplicantDTO, Applicant> getMapper() {
        return applicantMapper;
    }

    public ApplicantDTO getApplicantByUserId(long userId){
        return applicantMapper.toDto(applicantRepository.getByUserId(userId));
    }
}
