package nl.natuurverhaal.natuurverhaal.repositories;


import nl.natuurverhaal.natuurverhaal.models.UserProfile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserProfileRepository extends JpaRepository<UserProfile, Long> {

    Optional<List<UserProfile>> findByUser_Username(String username);


}
