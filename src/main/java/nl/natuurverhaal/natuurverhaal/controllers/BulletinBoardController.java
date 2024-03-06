package nl.natuurverhaal.natuurverhaal.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import nl.natuurverhaal.natuurverhaal.dtos.InputBulletinBoardDto;
import nl.natuurverhaal.natuurverhaal.dtos.OutputBulletinBoardDto;
import nl.natuurverhaal.natuurverhaal.services.BulletinBoardService;
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
@RequestMapping("/bulletin-boards")
public class BulletinBoardController {

        private final BulletinBoardService bulletinBoardService;

        public BulletinBoardController(BulletinBoardService bulletinBoardService) {
            this.bulletinBoardService = bulletinBoardService;
        }


        @GetMapping("/{username}/{id}")
    public ResponseEntity<OutputBulletinBoardDto> getBulletinBoard(@PathVariable("username") String username, @PathVariable("id") Long id) {
        OutputBulletinBoardDto bulletinBoard = bulletinBoardService.getBulletinBoard(username, id);
        return ResponseEntity.ok(bulletinBoard);
    }

    @GetMapping("/{username}")
    public ResponseEntity <List<OutputBulletinBoardDto>> getBulletinBoardByUsername(@PathVariable("username") String username) {
        List<OutputBulletinBoardDto> bulletinBoard = bulletinBoardService.getBulletinBoardByUsername(username);
        return ResponseEntity.ok(bulletinBoard);
    }


    @GetMapping
    public ResponseEntity<List<OutputBulletinBoardDto>> getAllBulletinBoards() {
        List<OutputBulletinBoardDto> bulletinBoard = bulletinBoardService.getAllBulletinBoards();
        return ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON).body(bulletinBoard);
    }




    @PostMapping("/{username}")
    public ResponseEntity<OutputBulletinBoardDto> createBulletinBoard(@RequestPart("file") MultipartFile file,
                                                            @RequestPart("username") String username,
                                                            @RequestPart("caption") String caption,
                                                            @RequestPart("title") String title,
                                                            @RequestPart("content") String content)
            throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        LocalDateTime currentDateAndTime = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
        String formattedDateTime = currentDateAndTime.format(formatter);

        System.out.println("file: " + file);
        System.out.println("username: " + username);
        System.out.println("caption: " + caption);

        InputBulletinBoardDto bulletinBoard = new InputBulletinBoardDto();
        bulletinBoard.setCaption(caption);
        bulletinBoard.setTitle(title);
        bulletinBoard.setContent(content);
        bulletinBoard.setUsername(username);
        bulletinBoard.setFile(file);
        bulletinBoard.setDate(formattedDateTime);
        OutputBulletinBoardDto createdBulletinBoard = bulletinBoardService.createBulletinBoard(bulletinBoard);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdBulletinBoard);
    }



    @DeleteMapping("/{username}/{id}")
    public ResponseEntity<Void> deleteBulletinBoard(@PathVariable("username") String username, @PathVariable("id") Long id) {
        bulletinBoardService.deleteBulletinBoard(username, id);
        return ResponseEntity.noContent().build();
    }

}

