package nl.novi.EindopdrachtBackend.repositories;

import nl.novi.EindopdrachtBackend.models.Receipt;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReceiptRepository extends JpaRepository<Receipt, Long> {
}
