package nl.novi.EindopdrachtBackend.repositories;

import nl.novi.EindopdrachtBackend.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, String> {
    public Optional<User> findById(Long employeeId);
}
