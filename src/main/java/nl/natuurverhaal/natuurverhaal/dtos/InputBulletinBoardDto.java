package nl.natuurverhaal.natuurverhaal.dtos;

import lombok.Data;
import nl.natuurverhaal.natuurverhaal.utils.Category;
import org.springframework.web.multipart.MultipartFile;

import java.util.Set;

@Data
public class InputBulletinBoardDto {

        private Long id;

        private String title;
        private String caption;
        private String content;
        private String username;
        private MultipartFile file;
        private String date;
    }


