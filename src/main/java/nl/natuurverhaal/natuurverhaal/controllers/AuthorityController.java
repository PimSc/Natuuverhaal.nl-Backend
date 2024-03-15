package nl.natuurverhaal.natuurverhaal.controllers;

import nl.natuurverhaal.natuurverhaal.exceptions.BadRequestException;
import nl.natuurverhaal.natuurverhaal.services.AuthorityService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@CrossOrigin
@RestController
@RequestMapping(value = "/users")
public class AuthorityController {

    private final AuthorityService authorityService;

    public AuthorityController( AuthorityService authorityService) {

        this.authorityService = authorityService;
    }

    @GetMapping(value = "/{username}/authorities")
    public ResponseEntity<Object> getUserAuthorities(@PathVariable("username") String username) {
        return ResponseEntity.ok().body(authorityService.getAuthorities(username));
    }

    @PostMapping(value = "/{username}/authorities")
    public ResponseEntity<Object> addUserAuthority(@PathVariable("username") String username, @RequestBody Map<String, Object> fields) {
        try {
            String authorityName = (String) fields.get("authority");
            authorityService.addAuthority(username, authorityName);
            return ResponseEntity.noContent().build();
        } catch (Exception ex) {
            throw new BadRequestException();
        }
    }

    @DeleteMapping(value = "/{username}/authorities/{authority}")
    public ResponseEntity<Object> deleteUserAuthority(@PathVariable("username") String username, @PathVariable("authority") String authority) {
        authorityService.removeAuthority(username, authority);
        return ResponseEntity.noContent().build();
    }
}