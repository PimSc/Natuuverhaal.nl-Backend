package nl.natuurverhaal.natuurverhaal.dtos;

import lombok.Data;

    @Data
    public class OutputUpvoteDto {

        private Long id;

        private String username;
        private Long blogPostId;
    }

