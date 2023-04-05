package com.recrutement.repositories;

import com.recrutement.entities.Company;
import com.recrutement.modules.base.BaseRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CompanyRepository extends BaseRepository<Company> {
    Company getByUserId(long userId);
}
