package com.recrutement.repositories;

import com.recrutement.entities.JobOffer;
import com.recrutement.modules.base.BaseRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface JobOfferRepository extends BaseRepository<JobOffer> {
    @Modifying
    @Query("UPDATE JobOffer jo SET jo.isOpen = ?2 WHERE jo.id = ?1")
    void updateIsOpen(long id, boolean status);
}
