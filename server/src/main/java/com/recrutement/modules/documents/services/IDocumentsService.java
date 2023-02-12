package com.recrutement.modules.documents.services;

import com.recrutement.exceptions.FileUploadException;
import com.recrutement.modules.documents.Document;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileNotFoundException;
import java.io.IOException;

public interface IDocumentsService {
    Document upload(MultipartFile file, String upload_folder) throws FileUploadException;

    Document findById(Long id) throws FileNotFoundException;

    void delete(Document document);

    void deleteById(Long id);
}
