package nl.novi.EindopdrachtBackend.repositories;

import nl.novi.EindopdrachtBackend.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, String> {
}
