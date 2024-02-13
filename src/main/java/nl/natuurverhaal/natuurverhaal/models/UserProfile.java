package nl.natuurverhaal.natuurverhaal.models;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "user_profile_data")
public class UserProfile {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String email;
    private String name;
    private String regio;
    private String bio;

    @OneToOne
    @JoinColumn(name = "user_username", referencedColumnName = "username")
    private User user;


}
