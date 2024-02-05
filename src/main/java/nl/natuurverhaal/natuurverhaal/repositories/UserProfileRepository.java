package nl.natuurverhaal.natuurverhaal.repositories;

import nl.natuurverhaal.natuurverhaal.models.UserProfile;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserProfileRepository extends JpaRepository<UserProfile, Long> {
}
