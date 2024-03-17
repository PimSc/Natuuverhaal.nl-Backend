package nl.natuurverhaal.natuurverhaal.controllers;

import nl.natuurverhaal.natuurverhaal.dtos.InputExcursionRegistrationDto;
import nl.natuurverhaal.natuurverhaal.dtos.OutputExcursionRegistrationDto;
import nl.natuurverhaal.natuurverhaal.services.ExcursionRegistrationService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/registrations")
public class ExcursionRegistrationController {

    private final ExcursionRegistrationService service;

    public ExcursionRegistrationController(ExcursionRegistrationService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<OutputExcursionRegistrationDto> createRegistration(@RequestBody InputExcursionRegistrationDto registrationDto) {
        OutputExcursionRegistrationDto createdRegistration = service.createRegistration(registrationDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdRegistration);
    }
}