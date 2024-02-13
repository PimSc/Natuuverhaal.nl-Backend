package nl.natuurverhaal.natuurverhaal.services;

import nl.natuurverhaal.natuurverhaal.exceptions.RecordNotFoundException;
import nl.natuurverhaal.natuurverhaal.models.User;
import nl.natuurverhaal.natuurverhaal.models.UserProfile;
import nl.natuurverhaal.natuurverhaal.repositories.UserRepository;
import nl.natuurverhaal.natuurverhaal.repositories.UserProfileRepository;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Optional;

@Service
public class UserProfileService {

    private final UserProfileRepository userProfileRepository;
    private final UserRepository userRepository;

    public UserProfileService(UserProfileRepository userProfileRepository, UserRepository userRepository) {
        this.userProfileRepository = userProfileRepository;
        this.userRepository = userRepository;
    }

    public void updateUserProfile(String username, UserProfile userProfile) throws IOException {
        Optional<User> userOptional = userRepository.findById(username);

        if (userOptional.isPresent()) {
            User user = userOptional.get();
            userProfile.setUser(user);
            userProfileRepository.save(userProfile);
        } else {
            throw new RecordNotFoundException("Gebruiker niet gevonden met gebruikersnaam: " + username);
        }
    }


    public UserProfile getUserProfileByUsername(String username) {
        Optional<User> userOptional = userRepository.findById(username);

        if (userOptional.isPresent()) {
            User user = userOptional.get();
            return user.getUserProfile();
        } else {
            throw new RecordNotFoundException("Gebruiker niet gevonden met gebruikersnaam: " + username);
        }

    }
}



