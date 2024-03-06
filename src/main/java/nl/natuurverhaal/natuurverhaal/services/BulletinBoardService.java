package nl.natuurverhaal.natuurverhaal.services;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;

import nl.natuurverhaal.natuurverhaal.dtos.InputBlogpostDto;
import nl.natuurverhaal.natuurverhaal.dtos.InputBulletinBoardDto;
import nl.natuurverhaal.natuurverhaal.dtos.OutputBulletinBoardDto;
import nl.natuurverhaal.natuurverhaal.models.BlogPost;
import nl.natuurverhaal.natuurverhaal.models.BulletinBoard;
import nl.natuurverhaal.natuurverhaal.models.User;
import nl.natuurverhaal.natuurverhaal.repositories.BulletinBoardRepository;
import nl.natuurverhaal.natuurverhaal.repositories.UserRepository;
import nl.natuurverhaal.natuurverhaal.utils.ImageUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class BulletinBoardService {


        private final BulletinBoardRepository bulletinBoardRepository;
        private final UserRepository userRepository;


        public BulletinBoardService(BulletinBoardRepository bulletinBoardRepository, UserRepository userRepository) {
            this.bulletinBoardRepository = bulletinBoardRepository;
            this.userRepository = userRepository;
        }

        public List<BulletinBoard> getAllBulletinBoards(String username, long id) {
            Optional<User> user = userRepository.findById(username);
            if (user.isPresent()) {
                user.get();

            } else {
                throw new EntityNotFoundException("User with username " + username + " not found");
            }
            return bulletinBoardRepository.findAll();
        }

        public OutputBulletinBoardDto createBulletinBoard(InputBulletinBoardDto inputBulletinBoardDto) throws IOException {
            BulletinBoard bulletinBoard = new BulletinBoard();

            bulletinBoard.setCaption(inputBulletinBoardDto.getCaption());
            bulletinBoard.setContent(inputBulletinBoardDto.getContent());
            bulletinBoard.setTitle(inputBulletinBoardDto.getTitle());
            bulletinBoard.setImageData(inputBulletinBoardDto.getFile().getBytes());
            bulletinBoard.setImageData(ImageUtil.compressImage(inputBulletinBoardDto.getFile().getBytes()));
            bulletinBoard.setDate(inputBulletinBoardDto.getDate());


            if (inputBulletinBoardDto.getUsername() != null) {
                User user = new User();
                user.setUsername(inputBulletinBoardDto.getUsername());
                bulletinBoard.setUser(user);
            }


            bulletinBoardRepository.save(bulletinBoard);
            OutputBulletinBoardDto outputBulletinBoardDto = new OutputBulletinBoardDto();

            outputBulletinBoardDto.setCaption(bulletinBoard.getCaption());
            outputBulletinBoardDto.setContent(bulletinBoard.getContent());
            outputBulletinBoardDto.setTitle(bulletinBoard.getTitle());
            outputBulletinBoardDto.setUsername(bulletinBoard.getUser().getUsername());
            outputBulletinBoardDto.setId(bulletinBoard.getId());
            outputBulletinBoardDto.setDate(bulletinBoard.getDate());
            outputBulletinBoardDto.setFileContent(ImageUtil.decompressImage(bulletinBoard.getImageData()));
            return outputBulletinBoardDto;
        }


        @Transactional
        public OutputBulletinBoardDto getBulletinBoard(String username, Long id) {

            BulletinBoard bulletinBoard = bulletinBoardRepository.findByIdAndUser_Username(id, username)
                    .orElseThrow(() -> new EntityNotFoundException("BulletinBoard not found with username " + username + " and id " + id));

            OutputBulletinBoardDto outputBulletinBoardDto = new OutputBulletinBoardDto();
            outputBulletinBoardDto.setTitle(bulletinBoard.getTitle());
            outputBulletinBoardDto.setCaption(bulletinBoard.getCaption());
            outputBulletinBoardDto.setContent(bulletinBoard.getContent());
            outputBulletinBoardDto.setUsername(bulletinBoard.getUser().getUsername());
            outputBulletinBoardDto.setId(bulletinBoard.getId());
            outputBulletinBoardDto.setFileContent(ImageUtil.decompressImage(bulletinBoard.getImageData()));
            outputBulletinBoardDto.setDate(bulletinBoard.getDate());
            return outputBulletinBoardDto;
        }

        @Transactional
        public List<OutputBulletinBoardDto> getAllBulletinBoards() {
            List<BulletinBoard> bulletinBoardList = bulletinBoardRepository.findAll();

            List<OutputBulletinBoardDto> outputBulletinBoardDtoList = new ArrayList<>();

            for (BulletinBoard bulletinBoard : bulletinBoardList) {
                OutputBulletinBoardDto outputBulletinBoardDto = new OutputBulletinBoardDto();
                outputBulletinBoardDto.setTitle(bulletinBoard.getTitle());
                outputBulletinBoardDto.setCaption(bulletinBoard.getCaption());
                outputBulletinBoardDto.setFileContent(ImageUtil.decompressImage(bulletinBoard.getImageData()));
                outputBulletinBoardDto.setContent(bulletinBoard.getContent());
                outputBulletinBoardDto.setUsername(bulletinBoard.getUser().getUsername());
                outputBulletinBoardDto.setId(bulletinBoard.getId());
                outputBulletinBoardDto.setDate(bulletinBoard.getDate());
                outputBulletinBoardDtoList.add(outputBulletinBoardDto);
            }
            ;
            return outputBulletinBoardDtoList;
        }

    public BulletinBoard updateBulletinBoard(Long id, InputBulletinBoardDto inputBulletinBoardDto) throws IOException {
        BulletinBoard bulletinBoard = bulletinBoardRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Bulletinboard post post not found with id " + id));

        bulletinBoard.setCaption(inputBulletinBoardDto.getCaption());
        bulletinBoard.setContent(inputBulletinBoardDto.getContent());
        bulletinBoard.setTitle(inputBulletinBoardDto.getTitle());

        // Check if the file is provided
        if (inputBulletinBoardDto.getFile() != null) {
            // Update the image data if the file is provided
            byte[] imageData = ImageUtil.compressImage(inputBulletinBoardDto.getFile().getBytes());
            bulletinBoard.setImageData(imageData);
        }

        bulletinBoard.setDate(inputBulletinBoardDto.getDate());
        return bulletinBoardRepository.save(bulletinBoard);
    }

        @Transactional
        public List<OutputBulletinBoardDto> getBulletinBoardByUsername(String username) {
            List<BulletinBoard> bulletinBoardList = bulletinBoardRepository.findByUser_Username(username)
                    .orElseThrow(() -> new EntityNotFoundException("Bulletinboard not found with username " + username));

            List<OutputBulletinBoardDto> outputBulletinBoardDtoList = new ArrayList<>();

            for (BulletinBoard bulletinBoard : bulletinBoardList) {
                OutputBulletinBoardDto outputBulletinBoardDto = new OutputBulletinBoardDto();
                outputBulletinBoardDto.setTitle(bulletinBoard.getTitle());
                outputBulletinBoardDto.setCaption(bulletinBoard.getCaption());
                outputBulletinBoardDto.setContent(bulletinBoard.getContent());
                outputBulletinBoardDto.setUsername(bulletinBoard.getUser().getUsername());
                outputBulletinBoardDto.setId(bulletinBoard.getId());
                outputBulletinBoardDto.setFileContent(ImageUtil.decompressImage(bulletinBoard.getImageData()));
                outputBulletinBoardDto.setDate(bulletinBoard.getDate());
                outputBulletinBoardDtoList.add(outputBulletinBoardDto);
            }
            return outputBulletinBoardDtoList;
        }

        private static final Logger logger = LoggerFactory.getLogger(nl.natuurverhaal.natuurverhaal.services.BulletinBoardService.class);


//    @PreAuthorize("#username == authentication.principal.username.auth or hasRole('ROLE_ADMIN')")
//    public void deleteBulletinBoard(String username, Long id) {
//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//        logger.info("Authenticated user: " + authentication.getName());
//        logger.info("Authorities: " + authentication.getAuthorities());
//        BulletinBoard bulletinBoard = bulletinBoardRepository.findById(id)
//                .orElseThrow(() -> new EntityNotFoundException("Bulletin board not found"));
//
//        if (!bulletinBoard.getUser().getUsername().equals(username)) {
//            throw new AccessDeniedException("You are not allowed to delete this bulletin board");
//        }
//
//        bulletinBoardRepository.delete(bulletinBoard);
//    }

        public void deleteBulletinBoard(String username, Long id) {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

            BulletinBoard bulletinBoard = bulletinBoardRepository.findById(id)
                    .orElseThrow(() -> new EntityNotFoundException("bulletin board post not found"));
            User user = userRepository.findById(username).orElseThrow(() -> new EntityNotFoundException("User not found"));
            if (user.getAuthorities().stream().anyMatch(grantedAuthority -> grantedAuthority.getAuthority().equals("ROLE_ADMIN"))) {
                // kijkt of ROLE_ADMIN is en doet verwijderen
                bulletinBoardRepository.delete(bulletinBoard);
            } else if (bulletinBoard.getUser().getUsername().equals(username)) {
                bulletinBoardRepository.delete(bulletinBoard);
            } else {
                // denied
                throw new AccessDeniedException("You are not allowed to delete this bulletin board");
            }
        }
}
