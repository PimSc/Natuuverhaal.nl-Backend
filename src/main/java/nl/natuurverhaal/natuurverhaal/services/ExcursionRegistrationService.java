package nl.natuurverhaal.natuurverhaal.services;

import lombok.Data;
import nl.natuurverhaal.natuurverhaal.dtos.InputExcursionRegistrationDto;
import nl.natuurverhaal.natuurverhaal.dtos.OutputExcursionRegistrationDto;
import nl.natuurverhaal.natuurverhaal.models.ExcursionRegistration;
import nl.natuurverhaal.natuurverhaal.repositories.ExcursionRegistrationRepository;
import nl.natuurverhaal.natuurverhaal.repositories.ExcursieRepository;
import nl.natuurverhaal.natuurverhaal.models.Excursie;
import org.springframework.stereotype.Service;

@Data
@Service
public class ExcursionRegistrationService {

    private final ExcursionRegistrationRepository repository;
    private final ExcursieRepository excursieRepository;

    public ExcursionRegistrationService(ExcursionRegistrationRepository repository, ExcursieRepository excursieRepository) {
        this.repository = repository;
        this.excursieRepository = excursieRepository;
    }

    public OutputExcursionRegistrationDto createRegistration(InputExcursionRegistrationDto registrationDto) {
        // Convert DTO to entity
        ExcursionRegistration excursionRegistration = new ExcursionRegistration();
        excursionRegistration.setName(registrationDto.getName());
        excursionRegistration.setEmail(registrationDto.getEmail());

        Excursie excursie = excursieRepository.findById(registrationDto.getExcursieId()).orElse(null);
        if (excursie != null) {
            excursionRegistration.setExcursie(excursie);
            excursionRegistration.setExcursionTitle(excursie.getTitle());
        }

        // Save to database
        ExcursionRegistration savedExcursionRegistration = repository.save(excursionRegistration);

        // Convert entity back to DTO
        OutputExcursionRegistrationDto outputDto = new OutputExcursionRegistrationDto();
        outputDto.setId(savedExcursionRegistration.getId());
        outputDto.setName(savedExcursionRegistration.getName());
        outputDto.setEmail(savedExcursionRegistration.getEmail());
        outputDto.setExcursieTitle(savedExcursionRegistration.getExcursionTitle());

        return outputDto;
    }

    private String getExcursieTitle(Long excursieId) {
        // Fetch the Excursie entity from the database using its ID
        Excursie excursie = excursieRepository.findById(excursieId).orElse(null);
        return (excursie != null) ? excursie.getTitle() : null;
    }
}