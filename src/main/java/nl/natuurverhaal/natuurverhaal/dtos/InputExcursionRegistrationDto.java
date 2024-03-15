package nl.natuurverhaal.natuurverhaal.dtos;

import lombok.Data;

@Data
public class InputExcursionRegistrationDto {

    private String name;
    private String email;
    private Long excursieId;
}
