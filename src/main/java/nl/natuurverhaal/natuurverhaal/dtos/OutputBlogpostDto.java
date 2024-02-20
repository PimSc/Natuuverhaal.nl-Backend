package nl.natuurverhaal.natuurverhaal.dtos;

import lombok.Data;
import nl.natuurverhaal.natuurverhaal.utils.Category;

import java.time.LocalDateTime;
import java.util.Set;

@Data
public class OutputBlogpostDto {
    private Long id;

    private String title;
    private String subtitle;
    private String caption;
    private String content;
    private String username;
    private byte[] fileContent;
    private String date;
    private Set<Category> categories;
}

