package nl.natuurverhaal.natuurverhaal.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import nl.natuurverhaal.natuurverhaal.dtos.InputBlogpostDto;
import nl.natuurverhaal.natuurverhaal.dtos.InputExcursieDto;
import nl.natuurverhaal.natuurverhaal.dtos.OutputExcursieDto;
import nl.natuurverhaal.natuurverhaal.services.ExcursieService;
import nl.natuurverhaal.natuurverhaal.utils.Category;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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
        public ResponseEntity<OutputExcursieDto> createExcursie(@RequestPart("file") MultipartFile file,
                                                                @RequestPart("title") String title,
                                                                @RequestPart("caption") String caption,
                                                                @RequestPart("subtitle") String subtitle,
                                                                @RequestPart("username") String username,
                                                                @RequestPart("activity_date") String activity_date,
                                                                @RequestPart("activity_time") String activity_time,
                                                                @RequestPart("price") String price,
                                                                @RequestPart("location") String location,
                                                                @RequestPart("subject") String subject,
                                                                @RequestPart("guide") String guide,
                                                                @RequestPart("content") String content,
                                                                @RequestPart("max_participants") String max_participants)

                throws IOException {
            ObjectMapper objectMapper = new ObjectMapper();
            LocalDateTime currentDateAndTime = LocalDateTime.now();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
            String formattedDateTime = currentDateAndTime.format(formatter);

            InputExcursieDto excursie = new InputExcursieDto();
            excursie.setFile(file);
            excursie.setTitle(title);
            excursie.setCaption(caption);
            excursie.setSubtitle(subtitle);
            excursie.setUsername(username);
            excursie.setActivity_date(activity_date);
            excursie.setActivity_time(activity_time);
            excursie.setPrice(price);
            excursie.setLocation(location);
            excursie.setSubject(subject);
            excursie.setGuide(guide);
            excursie.setContent(content);
            excursie.setMax_participants(max_participants);
            excursie.setDate(formattedDateTime);
            OutputExcursieDto createdExcursie = excursieService.createExcursie(excursie);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdExcursie);
        }




    @PutMapping(value = "/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Void> updateExcursie(@PathVariable("id") Long id,
                                               @RequestParam(value = "file", required = false) MultipartFile file,
                                               @RequestPart("username") String username,
                                               @RequestPart("caption") String caption,
                                               @RequestPart("title") String title,
                                               @RequestPart("subtitle") String subtitle,
                                               @RequestPart("guide") String guide,
                                               @RequestPart("max_participants") String max_participants,
                                               @RequestPart("location") String location,
                                               @RequestPart("price") String price,
                                               @RequestPart("activity_time") String activity_time,
                                               @RequestPart("activity_date") String activity_date,
                                               @RequestPart("subject") String subject,
                                               @RequestPart("content") String content)
            throws IOException {
        LocalDateTime currentDateAndTime = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
        String formattedDateTime = currentDateAndTime.format(formatter);

        InputExcursieDto excursie = new InputExcursieDto();
        excursie.setCaption(caption);
        excursie.setTitle(title);
        excursie.setSubtitle(subtitle);
        excursie.setContent(content);
        excursie.setUsername(username);
        excursie.setFile(file);
        excursie.setDate(formattedDateTime);
        excursie.setActivity_date(activity_date);
        excursie.setActivity_time(activity_time);
        excursie.setPrice(price);
        excursie.setLocation(location);
        excursie.setSubject(subject);
        excursie.setGuide(guide);
        excursie.setMax_participants(max_participants);

        excursieService.updateExcursie(id, excursie);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteExcursie(@PathVariable Long id) {
        excursieService.deleteExcursie(id);
        return ResponseEntity.noContent().build();
    }

    }

