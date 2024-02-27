package nl.natuurverhaal.natuurverhaal.models;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "excursies")
public class Excursie {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;


        private String title;
        private String caption;
        private String subtitle;
        private String activity_date;
        private String activity_time;
        private String price;
        private String location;
        private String subject;
        private String niveau;
        private String guide;
        private String username;
        private Short current_participants;
        private Short max_participants;
        private String date;

        @Lob
        private byte[] imageData;

        @Column(columnDefinition = "TEXT")
        private String content;

        @ManyToOne(fetch = FetchType.LAZY)
        @JoinColumn(name = "user_username", referencedColumnName = "username")

        private User user;

    }


