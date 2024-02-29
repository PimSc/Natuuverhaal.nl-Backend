package nl.natuurverhaal.natuurverhaal.dtos;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class OutputExcursieDto {

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
        private String content;
        private String username;
        private String max_participants;
        private byte[] fileContent;
        private String date;
}
