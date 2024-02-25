package nl.natuurverhaal.natuurverhaal.models;

import jakarta.persistence.*;
import lombok.Data;
import nl.natuurverhaal.natuurverhaal.utils.Category;

import java.util.HashSet;
import java.util.Set;

@Data
@Entity
@Table(name = "blog_posts")
public class BlogPost {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    private String subtitle;
    private String caption;
    private String name;
    private String type;
    private String date;

    @Lob
    private byte[] imageData;

    @Column(columnDefinition = "TEXT")
    private String content;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_username", referencedColumnName = "username")

    private User user;

    @ElementCollection(targetClass = Category.class)
    @CollectionTable(name = "blog_post_categories", joinColumns = @JoinColumn(name = "blog_post_id"))
    @Column(name = "category")
    @Enumerated(EnumType.STRING)
    private Set<Category> categories = new HashSet<>();

}
