package nl.novi.EindopdrachtBackend.repositories;

import nl.novi.EindopdrachtBackend.models.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepository extends JpaRepository<Customer, Long> {
}
