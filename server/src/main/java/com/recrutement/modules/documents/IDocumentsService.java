package com.recrutement.modules.documents;

import com.recrutement.exceptions.FileUploadException;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileNotFoundException;

public interface IDocumentsService {
    Document upload(MultipartFile file, String upload_folder) throws FileUploadException;

    Document findById(Long id) throws FileNotFoundException;
}
