package nl.natuurverhaal.natuurverhaal.repositories;

import nl.natuurverhaal.natuurverhaal.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, String> {
    Optional<User> findByUsername(String username);


}
