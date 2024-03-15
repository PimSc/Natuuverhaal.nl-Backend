package nl.natuurverhaal.natuurverhaal.repositories;

import nl.natuurverhaal.natuurverhaal.models.ExcursionRegistration;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ExcursionRegistrationRepository extends JpaRepository<ExcursionRegistration, Long> {
}