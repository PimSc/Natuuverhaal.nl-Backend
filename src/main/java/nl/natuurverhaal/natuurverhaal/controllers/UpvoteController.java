package nl.natuurverhaal.natuurverhaal.controllers;

import nl.natuurverhaal.natuurverhaal.dtos.InputUpvoteDto;
import nl.natuurverhaal.natuurverhaal.dtos.OutputUpvoteDto;
import nl.natuurverhaal.natuurverhaal.services.UpvoteService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/upvotes")
public class UpvoteController {
    private final UpvoteService upvoteService;

    public UpvoteController(UpvoteService upvoteService) {
        this.upvoteService = upvoteService;
    }

    // constructor

    @PostMapping
    public ResponseEntity<OutputUpvoteDto> upvoteBlogPost(@RequestBody InputUpvoteDto inputUpvoteDto) {
        OutputUpvoteDto outputUpvoteDto = upvoteService.upvoteBlogPost(inputUpvoteDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(outputUpvoteDto);
    }
}