package nl.natuurverhaal.natuurverhaal.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import nl.natuurverhaal.natuurverhaal.dtos.InputExcursieDto;
import nl.natuurverhaal.natuurverhaal.dtos.OutputExcursieDto;
import nl.natuurverhaal.natuurverhaal.services.ExcursieService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@RestController
@RequestMapping("/excursies")
public class ExcursieController {

    

        private final ExcursieService excursieService;

        public ExcursieController(ExcursieService excursieService) {
            this.excursieService = excursieService;
        }


        @GetMapping("/{username}/{id}")
        public ResponseEntity<OutputExcursieDto> getExcursie(@PathVariable("username") String username, @PathVariable("id") Long id) {
            OutputExcursieDto excursie = excursieService.getExcursie(username, id);
            return ResponseEntity.ok(excursie);
        }

        @GetMapping("/{username}")
        public ResponseEntity <List<OutputExcursieDto>> getExcursieByUsername(@PathVariable("username") String username) {
            List<OutputExcursieDto> excursie = excursieService.getExcursieByUsername(username);
            return ResponseEntity.ok(excursie);
        }


        @GetMapping
        public ResponseEntity<List<OutputExcursieDto>> getAllExcursies() {
            List<OutputExcursieDto> excursie = excursieService.getAllExcursies();
            return ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON).body(excursie);
        }




        @PostMapping("/{username}")
        public ResponseEntity<OutputExcursieDto> createExcursie(@RequestPart("title") String title,
                                                                @RequestPart("caption") String caption,
                                                                @RequestPart("subtitle") String subtitle,
                                                                @RequestPart("username") String username,
                                                                @RequestPart("activity_date") String activity_date,
                                                                @RequestPart("activity_time") String activity_time,
                                                                @RequestPart("price") String price,
                                                                @RequestPart("location") String location,
                                                                @RequestPart("subject") String subject,
                                                                @RequestPart("niveau") String niveau,
                                                                @RequestPart("guide") String guide,
                                                                @RequestPart("content") String content,
                                                                @RequestPart("max_participants") Short max_participants,
                                                                @RequestPart("file") MultipartFile file)

                throws IOException {
            ObjectMapper objectMapper = new ObjectMapper();
            LocalDateTime currentDateAndTime = LocalDateTime.now();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
            String formattedDateTime = currentDateAndTime.format(formatter);

            System.out.println("file: " + file);
            System.out.println("username: " + username);
            System.out.println("caption: " + caption);

            InputExcursieDto excursie = new InputExcursieDto();
            excursie.setTitle(title);
            excursie.setCaption(caption);
            excursie.setSubtitle(subtitle);
            excursie.setUsername(username);
            excursie.setActivity_date(activity_date);
            excursie.setActivity_time(activity_time);
            excursie.setPrice(price);
            excursie.setLocation(location);
            excursie.setSubject(subject);
            excursie.setNiveau(niveau);
            excursie.setGuide(guide);
            excursie.setContent(content);
            excursie.setMax_participants(max_participants);
            excursie.setFile(file);
            excursie.setDate(formattedDateTime);
            OutputExcursieDto createdExcursie = excursieService.createExcursie(excursie);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdExcursie);
        }



        @DeleteMapping("/{username}/{id}")
        public ResponseEntity<Void> deleteExcursie(@PathVariable("username") String username, @PathVariable("id") Long id) {
            excursieService.deleteExcursie(username, id);
            return ResponseEntity.noContent().build();
        }

    }

