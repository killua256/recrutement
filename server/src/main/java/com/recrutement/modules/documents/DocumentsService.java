package com.recrutement.modules.documents;

import com.recrutement.exceptions.FileUploadException;
import com.recrutement.utils.UtilsService;
import net.sf.jmimemagic.Magic;
import net.sf.jmimemagic.MagicMatch;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.nio.file.Files;
import java.util.Calendar;
import java.util.Optional;

@Service
public class DocumentsService implements IDocumentsService {
    private static final Logger logger = LogManager.getLogger(DocumentsService.class);

    @Autowired
    private UtilsService utilsService;
    @Autowired
    private DocumentRepository documentRepository;

    @Override
    public Document upload(MultipartFile file, String upload_folder) throws FileUploadException {
        try {
            Document doc = new Document();
            doc.setOriginalName(file.getOriginalFilename());
            doc.setSize(file.getSize());
            doc.setPath(upload_folder);

            String extension = file.getOriginalFilename().substring(
                    file.getOriginalFilename().lastIndexOf(".")
            );
            String filename = Calendar.getInstance().getTimeInMillis() + extension;
            doc.setExtension(extension);
            doc.setNodeRef(filename);

            byte[] bytes = file.getBytes();
            String tempDir = utilsService.getTempDir(upload_folder);
            File dir = new File(tempDir);
            if (!dir.exists()) {
                dir.mkdirs();
            }
            File serverfile = new File(dir.getAbsolutePath() + File.separator + filename);
            MagicMatch match = Magic.getMagicMatch(serverfile, true);
            doc.setMimeType(match.getMimeType());

            BufferedOutputStream stream = new BufferedOutputStream(Files.newOutputStream(serverfile.toPath()));
            stream.write(bytes);
            stream.close();
            return documentRepository.save(doc);
        } catch (Exception e) {
            logger.error(e.getMessage());
            throw new FileUploadException("File upload failed");
        }
    }

    @Override
    public Document findById(Long id) throws FileNotFoundException {
        Optional<Document> optional = documentRepository.findById(id);
        if(optional.isEmpty()){
            throw new FileNotFoundException("Document not found");
        }
        return optional.get();
    }
}
