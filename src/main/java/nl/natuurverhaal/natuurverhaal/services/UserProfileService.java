package nl.natuurverhaal.natuurverhaal.services;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import nl.natuurverhaal.natuurverhaal.dtos.InputUserProfileDto;
import nl.natuurverhaal.natuurverhaal.dtos.OutputUserProfileDto;
import nl.natuurverhaal.natuurverhaal.models.User;
import nl.natuurverhaal.natuurverhaal.models.UserProfile;
import nl.natuurverhaal.natuurverhaal.repositories.UserProfileRepository;
import nl.natuurverhaal.natuurverhaal.utils.ImageUtil;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class UserProfileService {

    private final UserProfileRepository userProfileRepository;

    public UserProfileService(UserProfileRepository userProfileRepository) {
        this.userProfileRepository = userProfileRepository;
    }





    public OutputUserProfileDto createUserProfile(InputUserProfileDto inputUserProfileDto) throws IOException {
        UserProfile userProfile = new UserProfile();

        // Set values from DTO to UserProfile object
        userProfile.setEmail(inputUserProfileDto.getEmail());
        userProfile.setName(inputUserProfileDto.getName());
        userProfile.setRegio(inputUserProfileDto.getRegio());
        userProfile.setBio(inputUserProfileDto.getBio());
        userProfile.setImageData(ImageUtil.compressImage(inputUserProfileDto.getFile().getBytes()));


        // Assuming username is provided in the DTO
        if (inputUserProfileDto.getUsername() != null) {
            // Create a User object and set its username
            User user = new User();
            user.setUsername(inputUserProfileDto.getUsername());

            // Set the User object to the UserProfile
            userProfile.setUser(user);
        }

        // Save the UserProfile object to the repository
        userProfileRepository.save(userProfile);

        // Create an OutputUserProfileDto and set its values from the saved UserProfile object
        OutputUserProfileDto outputUserProfileDto = new OutputUserProfileDto();
        outputUserProfileDto.setEmail(userProfile.getEmail());
        outputUserProfileDto.setName(userProfile.getName());
        outputUserProfileDto.setRegio(userProfile.getRegio());
        outputUserProfileDto.setBio(userProfile.getBio());
        outputUserProfileDto.setId(userProfile.getId());
        outputUserProfileDto.setUsername(userProfile.getUser().getUsername());

        outputUserProfileDto.setFileContent(ImageUtil.decompressImage(userProfile.getImageData()));

        return outputUserProfileDto;
    }


//    public OutputUserProfileDto createUserProfile(InputUserProfileDto inputUserProfileDto) throws IOException {
//        UserProfile userProfile = new UserProfile();
//
//        inputUserProfileDto.setEmail(userProfile.getEmail());
//        inputUserProfileDto.setName(userProfile.getName());
//        inputUserProfileDto.setRegio(userProfile.getRegio());
//        inputUserProfileDto.setBio(userProfile.getBio());
//        userProfile.setImageData(ImageUtil.compressImage(inputUserProfileDto.getFile().getBytes()));
//
//        if (inputUserProfileDto.getUsername() != null) {
//            User user = new User();
//            user.setUsername(inputUserProfileDto.getUsername());
//            userProfile.setUser(user);
//        }
//
//        userProfileRepository.save(userProfile);
//        OutputUserProfileDto outputUserProfileDto = new OutputUserProfileDto();
//
//        outputUserProfileDto.setEmail(userProfile.getEmail());
//        outputUserProfileDto.setName(userProfile.getName());
//        outputUserProfileDto.setRegio(userProfile.getRegio());
//        outputUserProfileDto.setBio(userProfile.getBio());
//        outputUserProfileDto.setFileContent(ImageUtil.decompressImage(userProfile.getImageData()));
//        return outputUserProfileDto;
//    }


    @Transactional
    public List<OutputUserProfileDto> getUserProfileByUsername(String username) {
        List<UserProfile> userProfileList = userProfileRepository.findByUser_Username(username)
                .orElseThrow(() -> new EntityNotFoundException("User profile not found with username " + username));

        List<OutputUserProfileDto> outputUserProfileDtoList = new ArrayList<>();

        for (UserProfile userProfile : userProfileList) {
            OutputUserProfileDto outputUserProfileDto = new OutputUserProfileDto();

            outputUserProfileDto.setEmail(userProfile.getEmail());
            outputUserProfileDto.setName(userProfile.getName());
            outputUserProfileDto.setRegio(userProfile.getRegio());
            outputUserProfileDto.setBio(userProfile.getBio());
            outputUserProfileDto.setFileContent(ImageUtil.decompressImage(userProfile.getImageData()));
            outputUserProfileDtoList.add(outputUserProfileDto);
        }
        ;
        return  outputUserProfileDtoList;
    }
}




