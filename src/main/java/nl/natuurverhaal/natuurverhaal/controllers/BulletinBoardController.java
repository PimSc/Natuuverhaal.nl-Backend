package nl.natuurverhaal.natuurverhaal.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import nl.natuurverhaal.natuurverhaal.dtos.InputBulletinBoardDto;
import nl.natuurverhaal.natuurverhaal.dtos.OutputBulletinBoardDto;
import nl.natuurverhaal.natuurverhaal.services.BulletinBoardService;
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
        LocalDateTime currentDateAndTime = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
        String formattedDateTime = currentDateAndTime.format(formatter);

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

    @PutMapping(value = "/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Void> updateBulletinBoard(@PathVariable("id") Long id,
                                                    @RequestParam(value = "file", required = false) MultipartFile file,
                                               @RequestPart("username") String username,
                                               @RequestPart("caption") String caption,
                                               @RequestPart("content") String content,
                                               @RequestPart("title") String title)
            throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        LocalDateTime currentDateAndTime = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
        String formattedDateTime = currentDateAndTime.format(formatter);

        InputBulletinBoardDto bulletinBoard = new InputBulletinBoardDto();
        bulletinBoard.setCaption(caption);
        bulletinBoard.setTitle(title);
        bulletinBoard.setContent(content);
        bulletinBoard.setUsername(username);
        bulletinBoard.setFile(file);
        bulletinBoard.setDate(formattedDateTime);

        bulletinBoardService.updateBulletinBoard(id, bulletinBoard);
        return ResponseEntity.noContent().build();
    }


    @DeleteMapping("/{username}/{id}")
    public ResponseEntity<Void> deleteBulletinBoard(@PathVariable("username") String username, @PathVariable("id") Long id) {
        bulletinBoardService.deleteBulletinBoard(username, id);
        return ResponseEntity.noContent().build();
    }

}

