package nl.natuurverhaal.natuurverhaal.controllers;

import nl.natuurverhaal.natuurverhaal.models.ImageData;
import nl.natuurverhaal.natuurverhaal.models.User;
import nl.natuurverhaal.natuurverhaal.repositories.ImageDataRepository;
import nl.natuurverhaal.natuurverhaal.repositories.UserRepository;
import nl.natuurverhaal.natuurverhaal.services.ImageDataService;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.GetMapping;

import java.io.IOException;
import java.util.Optional;


@RestController
@RequestMapping("/image")
public class ImageController {

private final ImageDataService imageDataService;
private final ImageDataRepository imageDataRepository;
private final UserRepository userRepository;

    public ImageController(ImageDataService imageDataService, ImageDataRepository imageDataRepository, UserRepository userRepository) {
        this.imageDataService = imageDataService;
        this.imageDataRepository = imageDataRepository;
        this.userRepository = userRepository;
    }

    @PostMapping
    public ResponseEntity<String> uploadImage(@RequestParam("file")MultipartFile multipartFile, @RequestParam String username) throws IOException {
        String image = imageDataService.uploadImage(multipartFile, username);
        return ResponseEntity.ok("bestand is geupload, " + image);
    }

    @GetMapping("/{username}")
    public ResponseEntity<Object> downloadImage (@PathVariable("username") String username) throws IOException {
        byte[] image = imageDataService.downloadImage(username);
        Optional<User> user = userRepository.findById(username);
        Optional<ImageData> dbImageData = imageDataRepository.findById(user.get().getImageData().getId());
        MediaType mediaType = MediaType.valueOf(dbImageData.get().getType());
        return  ResponseEntity.ok().contentType(mediaType).body(image);
    }

    @DeleteMapping("/{username}")
    public ResponseEntity<String> deleteImage(@PathVariable String username) {
        Optional<User> user = userRepository.findById(username);
        if (user.isPresent()) {
            // Verwijder de profielafbeelding van de gebruiker als deze bestaat
            imageDataService.deleteImage(user.get());
            return ResponseEntity.ok("Profile image from " + username + " succesvol deleted.");
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
