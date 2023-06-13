package nl.novi.EindopdrachtBackend.services;

import jakarta.servlet.http.HttpServletRequest;
import nl.novi.EindopdrachtBackend.dtos.DocumentDto;
import nl.novi.EindopdrachtBackend.models.Document;
import nl.novi.EindopdrachtBackend.repositories.CustomerRepository;
import nl.novi.EindopdrachtBackend.repositories.DocumentRepository;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.*;

@Service
public class DocumentService {
    private final DocumentRepository documentRepository;
    private final CustomerRepository customerRepository;

    public DocumentService(DocumentRepository documentRepository,
                           CustomerRepository customerRepository) {
        this.documentRepository = documentRepository;
        this.customerRepository = customerRepository;
    }

    public ResponseEntity<List<DocumentDto>> getAllDocuments(Long customerId) {
        List<DocumentDto> returnDocuments = new ArrayList<>();
        List<Document> allDocuments = documentRepository.findAll();

        for (Document document : allDocuments) {
            if (document.getCustomer().getId().equals(customerId)) {
                returnDocuments.add(fromDocument(document));
            }
        }

        return ResponseEntity.ok(returnDocuments);
    }

    public String uploadDocument(Long customerId, MultipartFile file) throws IOException {
        String name = StringUtils.cleanPath(Objects.requireNonNull(file.getOriginalFilename()));
        Document document = new Document();
        document.setDocName(name);
        document.setDocFile(file.getBytes());
        document.setCustomer(customerRepository.findById(customerId).get());

        documentRepository.save(document);

        return name;
    }

    public ResponseEntity<byte[]> singleDocumentDownload(String docName, HttpServletRequest request) {

        Document document = documentRepository.findByDocName(docName).get();

        String mimeType = request.getServletContext().getMimeType(document.getDocName());

        return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION, "inline;fileName=" + document.getDocName()).body(document.getDocFile());
    }

    public static DocumentDto fromDocument(Document document) {
        DocumentDto dto = new DocumentDto();

        dto.setId(document.getId());
        dto.setDocName(document.getDocName());
        dto.setDocFile(document.getDocFile());

        return dto;
    }

    public static Document toDocument(DocumentDto dto) {
        Document document = new Document();

        document.setId(dto.getId());
        document.setDocName(dto.getDocName());
        document.setDocFile(dto.getDocFile());

        return document;
    }
}
