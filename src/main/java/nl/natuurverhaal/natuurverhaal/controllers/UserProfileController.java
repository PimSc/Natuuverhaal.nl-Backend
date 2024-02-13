package nl.natuurverhaal.natuurverhaal.controllers;

import nl.natuurverhaal.natuurverhaal.models.UserProfile;
import nl.natuurverhaal.natuurverhaal.services.UserProfileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
//specificeert het basispad voor alle eindpunten in deze controller.
@RequestMapping("/user-profile")
public class UserProfileController {

    private final UserProfileService userProfileService;
    public UserProfileController(UserProfileService userProfileService) {
        this.userProfileService = userProfileService;
    }




    @GetMapping("/{username}")
    public ResponseEntity<UserProfile> getUserProfile(@PathVariable String username) {
        UserProfile userProfile = userProfileService.getUserProfileByUsername(username);
        return ResponseEntity.ok(userProfile);
    }

    @PutMapping("/{username}")
    public ResponseEntity<Void> updateUserProfile(@PathVariable String username, @RequestBody UserProfile userProfile) throws IOException {
        userProfileService.updateUserProfile(username, userProfile);
        return ResponseEntity.noContent().build();
    }
}
