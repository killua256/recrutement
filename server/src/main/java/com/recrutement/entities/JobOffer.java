package com.recrutement.entities;

import com.recrutement.modules.base.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "Job_offer")
public class JobOffer extends BaseEntity {

    @Column(name = "title")
    private String title;

    @Column(name = "description", columnDefinition = "TEXT")
    private String description;

    @Column(name = "qualifications", columnDefinition = "TEXT")
    private String qualifications;

    @Column(name = "location")
    private String location;

    @Column(name = "salary")
    private Double salary;

    @Column(name = "is_open", columnDefinition = "boolean default false")
    private boolean isOpen = false;

    @ManyToOne(optional = false)
    @JoinColumn(name="company_id", nullable=false, updatable=false)
    private Company company;
}
