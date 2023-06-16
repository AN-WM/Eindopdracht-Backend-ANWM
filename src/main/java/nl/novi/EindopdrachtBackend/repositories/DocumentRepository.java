package nl.novi.EindopdrachtBackend.repositories;

import jakarta.transaction.Transactional;
import nl.novi.EindopdrachtBackend.models.Document;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

@Transactional
public interface DocumentRepository extends JpaRepository<Document, Long> {
        Optional<Document> findByDocName(String docName);
}
