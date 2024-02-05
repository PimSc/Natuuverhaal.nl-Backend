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

    public String updateUserProfile(String username, UserProfile userProfile) throws IOException {
        Optional<User> user = userRepository.findById(username);
        User user1 = user.get();

        // Werk de eigenschappen bij met de waarden van het bijgewerkte profiel
        UserProfile usrProfile = new UserProfile();
        usrProfile.setEmail(userProfile.getEmail());
        usrProfile.setName(userProfile.getName());
        usrProfile.setRegio(userProfile.getRegio());
        usrProfile.setBio(userProfile.getBio());
        usrProfile.setUser(user1);

        UserProfile savedProfile = userProfileRepository.save(userProfile);
        user1.setUserProfile(usrProfile);
        // Sla het bijgewerkte profiel op in de gebruiker
        userRepository.save(user1);
        return savedProfile.getName();
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



