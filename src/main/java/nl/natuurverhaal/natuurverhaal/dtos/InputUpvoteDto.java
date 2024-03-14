package nl.natuurverhaal.natuurverhaal.dtos;

import lombok.Data;

@Data
public class InputUpvoteDto {
    private String username;
    private Long blogPostId;

    // getters and setters
}