package nl.natuurverhaal.natuurverhaal.dtos;

import lombok.Data;

@Data
public class OutputExcursionRegistrationDto {

    private Long id;

    private String name;
    private String email;
    private String excursieTitle;
}