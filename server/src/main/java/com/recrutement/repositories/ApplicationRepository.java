package com.recrutement.repositories;

import com.recrutement.entities.Application;
import com.recrutement.entities.JobOffer;
import com.recrutement.modules.base.BaseRepository;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public interface ApplicationRepository extends BaseRepository<Application> {
    Set<Application> findByJobOfferId(Long id);
}
