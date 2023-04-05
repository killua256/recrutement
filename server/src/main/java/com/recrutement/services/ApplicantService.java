package com.recrutement.services;

import com.recrutement.dtos.compact.ApplicantDTO;
import com.recrutement.dtos.full.ApplicantFullDTO;
import com.recrutement.entities.Applicant;
import com.recrutement.mappers.compact.ApplicantMapper;
import com.recrutement.mappers.full.ApplicantFullMapper;
import com.recrutement.modules.base.BaseMapper;
import com.recrutement.modules.base.BaseRepository;
import com.recrutement.modules.base.BaseService;
import com.recrutement.repositories.ApplicantRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ApplicantService extends BaseService<Applicant, ApplicantDTO, ApplicantFullDTO> {

    private final ApplicantRepository applicantRepository;
    private final ApplicantMapper applicantMapper;
    private final ApplicantFullMapper applicantFullMapper;

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

    @Override
    protected BaseMapper<ApplicantFullDTO, Applicant> getFullMapper() {
        return applicantFullMapper;
    }

    public ApplicantDTO getApplicantByUserId(long userId){
        return applicantMapper.toDto(applicantRepository.getByUserId(userId));
    }
}
