package com.recrutement.modules.documents;

import com.recrutement.modules.base.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "documents")
public class Document extends BaseEntity {

    @Column(name = "node_ref", unique = true, nullable = false)
    private String nodeRef;
    @Column(name = "original_name")
    private String originalName;
    @Column(name = "path")
    private String path;
    @Column(name = "size")
    private Long size;
    @Column(name = "ext")
    private String extension;
    @Column(name = "mime_type")
    private String mimeType;
}
