package nl.natuurverhaal.natuurverhaal.models;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
public class Upvote {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private User user;

    @ManyToOne
    @JoinColumn(name = "blog_post_id", nullable = false)
    private BlogPost blogPost;
}