package nl.natuurverhaal.natuurverhaal.models;

import jakarta.persistence.*;
import lombok.Data;
import nl.natuurverhaal.natuurverhaal.utils.Category;

import java.util.HashSet;
import java.util.Set;

@Data
@Entity
@Table(name = "bulletin_board_posts")
public class BulletinBoard {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;

        private String title;
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

    }

