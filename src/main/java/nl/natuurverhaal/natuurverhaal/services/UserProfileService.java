package nl.natuurverhaal.natuurverhaal.services;

import nl.natuurverhaal.natuurverhaal.exceptions.RecordNotFoundException;
import nl.natuurverhaal.natuurverhaal.models.UserProfile;
import nl.natuurverhaal.natuurverhaal.repositories.UserProfileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserProfileService {

    private final UserProfileRepository userProfileRepository;

    public UserProfileService(UserProfileRepository userProfileRepository) {
        this.userProfileRepository = userProfileRepository;
    }

    public UserProfile getUserProfileByUsername(String username) {
        return userProfileRepository.findByUserUsername(username)
                .orElseThrow(() -> new RecordNotFoundException("User profile not found"));
    }

    public void updateUserProfile(String username, UserProfile userProfile) {
        UserProfile existingProfile = getUserProfileByUsername(username);
        // Voer bijwerkingen uit op bestaand profiel, bijv. existingProfile.setFirstName(userProfile.getFirstName());
        userProfileRepository.save(existingProfile);

    }
}

