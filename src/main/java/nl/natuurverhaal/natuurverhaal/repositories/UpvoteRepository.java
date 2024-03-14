package nl.natuurverhaal.natuurverhaal.repositories;

import nl.natuurverhaal.natuurverhaal.models.BlogPost;
import nl.natuurverhaal.natuurverhaal.models.Upvote;
import nl.natuurverhaal.natuurverhaal.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UpvoteRepository extends JpaRepository<Upvote, Long> {
    Optional<Upvote> findByUserAndBlogPost(User user, BlogPost blogPost);
}
