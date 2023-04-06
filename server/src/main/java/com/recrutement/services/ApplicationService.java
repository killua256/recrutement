package com.recrutement.services;

import com.querydsl.core.BooleanBuilder;
import com.recrutement.dtos.compact.ApplicationDTO;
import com.recrutement.dtos.full.ApplicationFullDTO;
import com.recrutement.entities.Application;
import com.recrutement.entities.QApplication;
import com.recrutement.enums.ApplicationStatus;
import com.recrutement.mappers.compact.ApplicationMapper;
import com.recrutement.mappers.full.ApplicationFullMapper;
import com.recrutement.modules.base.BaseMapper;
import com.recrutement.modules.base.BaseRepository;
import com.recrutement.modules.base.BaseService;
import com.recrutement.repositories.ApplicationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ApplicationService extends BaseService<Application, ApplicationDTO, ApplicationFullDTO> {

    private final ApplicationRepository applicationRepository;
    private final ApplicationMapper applicationMapper;
    private final ApplicationFullMapper applicationFullMapper;

    @Override
    protected Class<Application> getType() {
        return Application.class;
    }

    @Override
    protected BaseRepository<Application> getRepository() {
        return applicationRepository;
    }

    @Override
    protected BaseMapper<ApplicationDTO, Application> getMapper() {
        return applicationMapper;
    }

    @Override
    protected BaseMapper<ApplicationFullDTO, Application> getFullMapper() {
        return applicationFullMapper;
    }

    public void deleteApplicationFromJob(long applicantId, long jobOfferId){
        applicationRepository.deleteByApplicantIdAndJobOfferId(applicantId, jobOfferId);
    }

    public ApplicationDTO getApplication(long jobOfferId, long applicantId){
        return applicationMapper.toDto(applicationRepository.findByApplicantIdAndJobOfferId(jobOfferId, applicantId));
    }

    public boolean exists(long jobOfferId, long applicantId){
        return applicationRepository.existsByApplicantIdAndJobOfferId(jobOfferId, applicantId);
    }

    public ApplicationStatus getApplicationStatus(long applicationId, long applicantId){
        BooleanBuilder where = new BooleanBuilder();
        QApplication qApplication = QApplication.application;
        where.and(qApplication.applicant.id.eq(applicantId));
        where.and(qApplication.id.eq(applicationId));
        return applicationRepository.findOne(where).orElseThrow(() -> elementNotFoundHandler(where)).getStatus();
    }
}
