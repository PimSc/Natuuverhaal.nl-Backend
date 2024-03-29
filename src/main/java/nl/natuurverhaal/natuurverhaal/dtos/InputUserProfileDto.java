package nl.natuurverhaal.natuurverhaal.dtos;

import jakarta.validation.constraints.Size;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class InputUserProfileDto {

    private Long id;

    private String email;
    @Size(max=20,message="voldoet niet aan de criteria")
    private String name;
    private String regio;
    private String bio;
    private MultipartFile file;
    private String username;
}
