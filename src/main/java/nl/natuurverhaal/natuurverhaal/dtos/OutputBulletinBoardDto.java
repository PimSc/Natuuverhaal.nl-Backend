package nl.natuurverhaal.natuurverhaal.dtos;

import lombok.Data;
import nl.natuurverhaal.natuurverhaal.utils.Category;

import java.util.Set;

@Data
public class OutputBulletinBoardDto {

        private Long id;

        private String title;
        private String caption;
        private String content;
        private String username;
        private byte[] fileContent;
        private String date;
    }



