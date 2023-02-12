package com.recrutement.modules.documents;

import com.recrutement.modules.documents.services.IDocumentsService;
import com.recrutement.utils.UtilsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.*;
import java.nio.file.Files;

@RestController
@RequestMapping("/api/v1/docs")
public class DocumentController {
    private final Logger logger = LoggerFactory.getLogger(DocumentController.class);
    @Autowired
    private IDocumentsService documentsService;
    @Autowired
    private UtilsService utilsService;

    @GetMapping(value = "/{id}")
    public ResponseEntity<?> download(@PathVariable Long id) {
        try {
            Document doc = documentsService.findById(id);
            String dir = utilsService.getTempDir(doc.getPath());
            File fileImage = new File(dir + File.separator, doc.getNodeRef());
            byte[] resource = Files.readAllBytes(fileImage.toPath());
            MediaType mime = MediaType.parseMediaType(doc.getMimeType());
            return ResponseEntity.ok()
                    .contentType(mime)
                    .body(resource);
        } catch (FileNotFoundException e) {
            logger.error(e.getMessage());
            return new ResponseEntity<>("File not found", HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            logger.error(e.getMessage());
            return new ResponseEntity<>("Loading file failed", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
