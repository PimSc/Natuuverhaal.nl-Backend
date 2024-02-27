package nl.natuurverhaal.natuurverhaal.repositories;

import nl.natuurverhaal.natuurverhaal.models.BlogPost;
import nl.natuurverhaal.natuurverhaal.models.BulletinBoard;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public interface BulletinBoardRepository extends JpaRepository<BulletinBoard, Long> {

        Optional<BulletinBoard> findByIdAndUser_Username(Long id, String username);

        Optional<List<BulletinBoard>> findByUser_Username(String username);

//    Optional<List<BulletinBoard>> getAllBulletinBoards();

    }



