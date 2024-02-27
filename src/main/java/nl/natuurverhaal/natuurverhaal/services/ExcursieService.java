package nl.natuurverhaal.natuurverhaal.services;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import nl.natuurverhaal.natuurverhaal.dtos.InputExcursieDto;
import nl.natuurverhaal.natuurverhaal.dtos.OutputExcursieDto;
import nl.natuurverhaal.natuurverhaal.models.Excursie;
import nl.natuurverhaal.natuurverhaal.models.User;
import nl.natuurverhaal.natuurverhaal.repositories.ExcursieRepository;

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
public class ExcursieService {
    

        private final ExcursieRepository excursieRepository;
        private final UserRepository userRepository;


        public ExcursieService(ExcursieRepository excursieRepository, UserRepository userRepository) {
            this.excursieRepository = excursieRepository;
            this.userRepository = userRepository;
        }

        public List<Excursie> getAllExcursies(String username, long id) {
            Optional<User> user = userRepository.findById(username);
            if (user.isPresent()) {
                user.get();

            } else {
                throw new EntityNotFoundException("User with username " + username + " not found");
            }
            return excursieRepository.findAll();
        }

        public OutputExcursieDto createExcursie(InputExcursieDto inputExcursieDto) throws IOException {
            Excursie excursie = new Excursie();

            excursie.setTitle(inputExcursieDto.getTitle());
            excursie.setCaption(inputExcursieDto.getCaption());
            excursie.setSubtitle(inputExcursieDto.getSubtitle());
            excursie.setActivity_date(inputExcursieDto.getActivity_date());
            excursie.setActivity_time(inputExcursieDto.getActivity_time());
            excursie.setPrice(inputExcursieDto.getPrice());
            excursie.setLocation(inputExcursieDto.getLocation());
            excursie.setSubject(inputExcursieDto.getSubject());
            excursie.setNiveau(inputExcursieDto.getNiveau());
            excursie.setGuide(inputExcursieDto.getGuide());
            excursie.setContent(inputExcursieDto.getContent());
            excursie.setMax_participants(inputExcursieDto.getMax_participants());
            excursie.setImageData(inputExcursieDto.getFile().getBytes());
            excursie.setImageData(ImageUtil.compressImage(inputExcursieDto.getFile().getBytes()));
            excursie.setDate(inputExcursieDto.getDate());



            if (inputExcursieDto.getUsername() != null) {
                User user = new User();
                user.setUsername(inputExcursieDto.getUsername());
                excursie.setUser(user);
            }


            excursieRepository.save(excursie);
            OutputExcursieDto outputExcursieDto = new OutputExcursieDto();

            outputExcursieDto.setTitle(excursie.getTitle());
            outputExcursieDto.setCaption(excursie.getCaption());
            outputExcursieDto.setSubtitle(excursie.getSubtitle());
            outputExcursieDto.setActivity_date(excursie.getActivity_date());
            outputExcursieDto.setActivity_time(excursie.getActivity_time());
            outputExcursieDto.setPrice(excursie.getPrice());
            outputExcursieDto.setLocation(excursie.getLocation());
            outputExcursieDto.setSubject(excursie.getSubject());
            outputExcursieDto.setNiveau(excursie.getNiveau());
            outputExcursieDto.setGuide(excursie.getGuide());
            outputExcursieDto.setContent(excursie.getContent());
            outputExcursieDto.setUsername(excursie.getUser().getUsername());
            outputExcursieDto.setMax_participants(excursie.getMax_participants());
            outputExcursieDto.setId(excursie.getId());
            outputExcursieDto.setFileContent(ImageUtil.decompressImage(excursie.getImageData()));
            outputExcursieDto.setDate(excursie.getDate());

            return outputExcursieDto;
        }


        @Transactional
        public OutputExcursieDto getExcursie(String username, Long id) {

            Excursie excursie = excursieRepository.findByIdAndUser_Username(id, username)
                    .orElseThrow(() -> new EntityNotFoundException("Excursie not found with username " + username + " and id " + id));

            OutputExcursieDto outputExcursieDto = new OutputExcursieDto();
            outputExcursieDto.setTitle(excursie.getTitle());
            outputExcursieDto.setCaption(excursie.getCaption());
            outputExcursieDto.setSubtitle(excursie.getSubtitle());
            outputExcursieDto.setActivity_date(excursie.getActivity_date());
            outputExcursieDto.setActivity_time(excursie.getActivity_time());
            outputExcursieDto.setPrice(excursie.getPrice());
            outputExcursieDto.setLocation(excursie.getLocation());
            outputExcursieDto.setSubject(excursie.getSubject());
            outputExcursieDto.setNiveau(excursie.getNiveau());
            outputExcursieDto.setGuide(excursie.getGuide());
            outputExcursieDto.setContent(excursie.getContent());
            outputExcursieDto.setUsername(excursie.getUser().getUsername());
            outputExcursieDto.setMax_participants(excursie.getMax_participants());
            outputExcursieDto.setId(excursie.getId());
            outputExcursieDto.setFileContent(ImageUtil.decompressImage(excursie.getImageData()));
            outputExcursieDto.setDate(excursie.getDate());

            return outputExcursieDto;
        }

        @Transactional
        public List<OutputExcursieDto> getAllExcursies() {
            List<Excursie> excursieList = excursieRepository.findAll();

            List<OutputExcursieDto> outputExcursieDtoList = new ArrayList<>();

            for (Excursie excursie : excursieList) {
                OutputExcursieDto outputExcursieDto = new OutputExcursieDto();
                outputExcursieDto.setTitle(excursie.getTitle());
                outputExcursieDto.setCaption(excursie.getCaption());
                outputExcursieDto.setSubtitle(excursie.getSubtitle());
                outputExcursieDto.setActivity_date(excursie.getActivity_date());
                outputExcursieDto.setActivity_time(excursie.getActivity_time());
                outputExcursieDto.setPrice(excursie.getPrice());
                outputExcursieDto.setLocation(excursie.getLocation());
                outputExcursieDto.setSubject(excursie.getSubject());
                outputExcursieDto.setNiveau(excursie.getNiveau());
                outputExcursieDto.setGuide(excursie.getGuide());
                outputExcursieDto.setContent(excursie.getContent());
                outputExcursieDto.setUsername(excursie.getUser().getUsername());
                outputExcursieDto.setMax_participants(excursie.getMax_participants());
                outputExcursieDto.setId(excursie.getId());
                outputExcursieDto.setFileContent(ImageUtil.decompressImage(excursie.getImageData()));
                outputExcursieDto.setDate(excursie.getDate());
                outputExcursieDtoList.add(outputExcursieDto);
            }
            return outputExcursieDtoList;
        }

        @Transactional
        public List<OutputExcursieDto> getExcursieByUsername(String username) {
            List<Excursie> excursieList = excursieRepository.findByUser_Username(username)
                    .orElseThrow(() -> new EntityNotFoundException("Excursie not found with username " + username));

            List<OutputExcursieDto> outputExcursieDtoList = new ArrayList<>();

            for (Excursie excursie : excursieList) {
                OutputExcursieDto outputExcursieDto = new OutputExcursieDto();
                outputExcursieDto.setTitle(excursie.getTitle());
                outputExcursieDto.setCaption(excursie.getCaption());
                outputExcursieDto.setSubtitle(excursie.getSubtitle());
                outputExcursieDto.setActivity_date(excursie.getActivity_date());
                outputExcursieDto.setActivity_time(excursie.getActivity_time());
                outputExcursieDto.setPrice(excursie.getPrice());
                outputExcursieDto.setLocation(excursie.getLocation());
                outputExcursieDto.setSubject(excursie.getSubject());
                outputExcursieDto.setNiveau(excursie.getNiveau());
                outputExcursieDto.setGuide(excursie.getGuide());
                outputExcursieDto.setContent(excursie.getContent());
                outputExcursieDto.setUsername(excursie.getUser().getUsername());
                outputExcursieDto.setMax_participants(excursie.getMax_participants());
                outputExcursieDto.setId(excursie.getId());
                outputExcursieDto.setFileContent(ImageUtil.decompressImage(excursie.getImageData()));
                outputExcursieDto.setDate(excursie.getDate());
                outputExcursieDtoList.add(outputExcursieDto);
            }
            return outputExcursieDtoList;
        }

        private static final Logger logger = LoggerFactory.getLogger(nl.natuurverhaal.natuurverhaal.services.ExcursieService.class);


//    @PreAuthorize("#username == authentication.principal.username.auth or hasRole('ROLE_ADMIN')")
//    public void deleteExcursie(String username, Long id) {
//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//        logger.info("Authenticated user: " + authentication.getName());
//        logger.info("Authorities: " + authentication.getAuthorities());
//        Excursie excursie = excursieRepository.findById(id)
//                .orElseThrow(() -> new EntityNotFoundException("Excursie  not found"));
//
//        if (!excursie.getUser().getUsername().equals(username)) {
//            throw new AccessDeniedException("You are not allowed to delete this excursie ");
//        }
//
//        excursieRepository.delete(excursie);
//    }

        public void deleteExcursie(String username, Long id) {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

            Excursie excursie = excursieRepository.findById(id)
                    .orElseThrow(() -> new EntityNotFoundException("excursie  post not found"));
            User user = userRepository.findById(username).orElseThrow(() -> new EntityNotFoundException("User not found"));
            if (user.getAuthorities().stream().anyMatch(grantedAuthority -> grantedAuthority.getAuthority().equals("ROLE_ADMIN"))) {
                // kijkt of ROLE_ADMIN is en doet verwijderen
                excursieRepository.delete(excursie);
            } else if (excursie.getUser().getUsername().equals(username)) {
                excursieRepository.delete(excursie);
            } else {
                // denied
                throw new AccessDeniedException("You are not allowed to delete this excursie ");
            }
        }
}
