package nl.natuurverhaal.natuurverhaal.repositories;


import nl.natuurverhaal.natuurverhaal.models.Excursie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ExcursieRepository extends JpaRepository<Excursie, Long>{

        Optional<Excursie> findByIdAndUser_Username(Long id, String username);

        Optional<List<Excursie>> findByUser_Username(String username);


    }


