package com.recrutement.entities;

import com.recrutement.modules.base.BaseEntity;
import com.recrutement.modules.user.User;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "company")
public class Company extends BaseEntity {

    @Column(name = "company_name")
    private String companyName;
    @Column(name = "website_url")
    private String websiteUrl;
    @Column(name = "company_description")
    private String companyDescription;
    @OneToOne
    private User user;

}
