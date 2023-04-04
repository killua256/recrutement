package com.recrutement.entities;

import com.recrutement.modules.base.BaseEntity;
import com.recrutement.modules.user.User;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "applicant")
public class Applicant extends BaseEntity {
    @Column(name = "resume_url")
    private String resumeUrl;
    @Column(name = "skills")
    private String skills;
    @Column(name = "education")
    private String education;
    @OneToOne
    private User user;
}
