package nl.natuurverhaal.natuurverhaal.dtos;

import jakarta.persistence.Column;
import lombok.Data;
import nl.natuurverhaal.natuurverhaal.models.User;
import org.springframework.web.multipart.MultipartFile;

@Data
public class InputBlogpostDto {
    private Long id;

    private String title;
    private String subtitle;
    private String caption;
    private String content;
    private String username;
    private MultipartFile file;
}
