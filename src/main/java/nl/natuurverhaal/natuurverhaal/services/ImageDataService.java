package nl.natuurverhaal.natuurverhaal.services;

import lombok.Data;
import nl.natuurverhaal.natuurverhaal.models.ImageData;
import nl.natuurverhaal.natuurverhaal.models.User;
import nl.natuurverhaal.natuurverhaal.repositories.ImageDataRepository;
import nl.natuurverhaal.natuurverhaal.repositories.UserRepository;
import nl.natuurverhaal.natuurverhaal.utils.ImageUtil;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Optional;

@Service
@Data
public class ImageDataService {

    private final ImageDataRepository imageDataRepository;
    private final UserRepository userRepository;

    public ImageDataService(ImageDataRepository imageDataRepository, UserRepository userRepository) {
        this.imageDataRepository = imageDataRepository;
        this.userRepository = userRepository;
    }
    public String uploadImage(MultipartFile multipartFile, String username) throws IOException {
        //        Dit in een if statement zetten?
        Optional<User> user = userRepository.findById(username);
        User user1 = user.get();

//        Maakt een nieuwe image data aan en zet de naam, type en imageData
//        van de image data naar de gegevens van de multipart file

        ImageData imgData = new ImageData();
        imgData.setName(multipartFile.getName());
        imgData.setType(multipartFile.getContentType());
        imgData.setImageData(ImageUtil.compressImage(multipartFile.getBytes()));

        ImageData savedImage = imageDataRepository.save(imgData);
        user1.setImage(imgData);
        userRepository.save(user1);
        return savedImage.getName();
    }

    public byte[] downloadImage(String username) throws IOException {
        Optional<User> user = userRepository.findById(username);
        User user1 = user.get();
//        if (user. isPresent()) {
//            user1 = user.get();
//        }
        ImageData imageData = user1.getImageData();
        return ImageUtil.decompressImage(imageData.getImageData());
    }
}