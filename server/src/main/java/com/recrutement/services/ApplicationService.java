package com.recrutement.services;

import com.recrutement.DTOs.ApplicationDTO;
import com.recrutement.entities.Application;
import com.recrutement.mappers.ApplicationMapper;
import com.recrutement.modules.base.BaseMapper;
import com.recrutement.modules.base.BaseRepository;
import com.recrutement.modules.base.BaseService;
import com.recrutement.repositories.ApplicationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ApplicationService extends BaseService<Application, ApplicationDTO> {

    private final ApplicationRepository applicationRepository;
    private final ApplicationMapper applicationMapper;

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
}
