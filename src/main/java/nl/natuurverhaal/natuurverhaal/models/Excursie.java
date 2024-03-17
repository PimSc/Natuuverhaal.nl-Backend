package nl.natuurverhaal.natuurverhaal.models;

import jakarta.persistence.*;
import lombok.Data;
import nl.natuurverhaal.natuurverhaal.models.ExcursionRegistration;
import java.util.List;

@Data
@Entity
@Table(name = "excursie")
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
        private String guide;
        private String username;
        private String max_participants;
        private String name;
        private String type;
        private String date;


        @OneToMany(mappedBy = "excursie", cascade = CascadeType.REMOVE)
        private List<ExcursionRegistration> registrations;

        @Lob
        private byte[] imageData;

        @Column(columnDefinition = "TEXT")
        private String content;

        @ManyToOne(fetch = FetchType.LAZY)
        @JoinColumn(name = "user_username", referencedColumnName = "username")

        private User user;

    }


