package com.recrutement.modules.base;

import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.annotations.Where;

import java.util.Date;

@Inheritance(strategy= InheritanceType.TABLE_PER_CLASS)
@MappedSuperclass
@Getter
@Setter
@EqualsAndHashCode
@Where(clause = "is_enabled = true")
public class BaseEntity {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    protected Long id;
    @Column(name = "created_at")
    @CreationTimestamp
    protected Date createdAt;
    @Column(name = "updated_at")
    @UpdateTimestamp
    protected Date updatedAt;
    @Column(name = "is_enabled", columnDefinition = "boolean default true")
    protected Boolean isEnabled = true;

    @PrePersist
    private void onCreate() {
        if(createdAt == null){
            createdAt = new Date();
        }
        if(isEnabled == null){
            isEnabled = true;
        }
    }
}