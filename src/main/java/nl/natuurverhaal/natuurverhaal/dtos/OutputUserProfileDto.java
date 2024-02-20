package nl.natuurverhaal.natuurverhaal.dtos;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class OutputUserProfileDto {
        private Long id;

        private String email;
        private String name;
        private String regio;
        private String bio;
        private byte[] fileContent;
        private String username;
    }

