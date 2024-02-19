package nl.natuurverhaal.natuurverhaal.dtos;

import lombok.Data;

@Data
public class OutputBlogpostDto {
    private Long id;

    private String title;
    private String subtitle;
    private String caption;
    private String content;
    private String username;
    private byte[] fileContent;
}

