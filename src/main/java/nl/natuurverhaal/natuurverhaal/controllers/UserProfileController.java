package nl.natuurverhaal.natuurverhaal.controllers;
import jakarta.validation.Valid;
import nl.natuurverhaal.natuurverhaal.dtos.InputUserProfileDto;
import nl.natuurverhaal.natuurverhaal.dtos.OutputBlogpostDto;
import nl.natuurverhaal.natuurverhaal.dtos.OutputUserProfileDto;
import nl.natuurverhaal.natuurverhaal.services.UserProfileService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.util.List;


@RestController
@RequestMapping("/user-profile")
public class UserProfileController {

    private final UserProfileService userProfileService;
    public UserProfileController(UserProfileService userProfileService) {
        this.userProfileService = userProfileService;
    }

    @GetMapping
    public ResponseEntity <List<OutputUserProfileDto>> getAllUserProfiles() {
        List<OutputUserProfileDto> allUserProfiles = userProfileService.getAlleUserProfiles();
        return ResponseEntity.ok(allUserProfiles);
    }

    @GetMapping("/{username}")
    public ResponseEntity <List<OutputUserProfileDto>> getUserProfileByUsername(@PathVariable("username") String username) {
        List<OutputUserProfileDto> userProfile = userProfileService.getUserProfileByUsername(username);
        return ResponseEntity.ok(userProfile);
    }

    @PostMapping("/{username}")
    public ResponseEntity<OutputUserProfileDto> createUserProfile(@Valid @RequestPart("email") String email,
                                                                   @RequestPart("name") String name,
                                                            @RequestPart("regio") String regio,
                                                            @RequestPart("bio") String bio,
                                                            @RequestPart("file") MultipartFile file,
                                                             @RequestPart("username") String username)  throws IOException {
        System.out.println("file: " + file);
        InputUserProfileDto userProfile = new InputUserProfileDto();
        userProfile.setEmail(email);
        userProfile.setName(name);
        userProfile.setRegio(regio);
        userProfile.setBio(bio);
        userProfile.setFile(file);
        userProfile.setUsername(username);
        OutputUserProfileDto createdProfile = userProfileService.createUserProfile(userProfile);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdProfile);
    }
}
