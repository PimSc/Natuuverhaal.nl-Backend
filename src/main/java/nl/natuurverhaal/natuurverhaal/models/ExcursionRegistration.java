package nl.natuurverhaal.natuurverhaal.models;

import jakarta.persistence.*;
import lombok.Data;
import nl.natuurverhaal.natuurverhaal.models.Excursie;

@Data
@Entity
@Table(name = "excursion_registration")
public class ExcursionRegistration {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String email;

    @ManyToOne
    private Excursie excursie;

    private String excursionTitle;
}