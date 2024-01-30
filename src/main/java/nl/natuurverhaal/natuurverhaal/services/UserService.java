//UserService is verantwoordelijk voor het verwerken van gebruikersgerelateerde
// logica en interactie met de database




//Validatie:
//        Voeg validatie toe voor invoergegevens in de createUser-methode om te controleren of vereiste velden zijn ingevuld en of het wachtwoord sterk genoeg is.
//
//        Encryptie van wachtwoorden:
//        Voeg wachtwoordencryptie toe aan de User-entiteit. Momenteel wordt het wachtwoord rechtstreeks ingesteld en opgeslagen. Overweeg bijvoorbeeld bcrypt om wachtwoorden veilig op te slaan.
//
//        Update andere gebruikersgegevens:
//        De updateUser-methode werkt momenteel alleen het wachtwoord bij. Overweeg om andere gebruikersgegevens zoals e-mail te kunnen bijwerken.
//
//        Gebruikersactiveringsfunctionaliteit:
//        Voeg functionaliteit toe voor het activeren/deactiveren van gebruikersaccounts. Momenteel heeft de User-entiteit een enabled-veld, maar er wordt geen gebruik van gemaakt.
//
//        Logging:
//        Voeg logging toe om belangrijke gebeurtenissen of fouten vast te leggen.
//
//        Paginering en sortering:
//        Als het aantal gebruikers groot kan worden, overweeg dan paginering en sortering bij het ophalen van gebruikers.
//
//        Beveiliging:
//        Overweeg verdere beveiligingscontroles, afhankelijk van de aard van je applicatie. Bijvoorbeeld het beperken van welke gebruikers welke acties kunnen uitvoeren.
//
//        Testen:
//        Voeg tests toe om ervoor te zorgen dat de service correct werkt in verschillende scenario's. Unit-tests en integratietests kunnen helpen de betrouwbaarheid van je service te waarborgen.
//
//        Documentatie:
//        Voeg documentatie toe, vooral als dit deel uitmaakt van een API. Geef duidelijk aan welke eindpunten beschikbaar zijn en welke parameters ze accepteren.
//
//        Transaction Management:
//        Overweeg om transactiemanagement toe te voegen, vooral bij het uitvoeren van meerdere databasebewerkingen in één methode.
//
//        Het is belangrijk om functionaliteiten toe te voegen op basis van specifieke behoeften van je applicatie en je gebruikers.





package nl.natuurverhaal.natuurverhaal.services;

import nl.natuurverhaal.natuurverhaal.dtos.UserDto;
import nl.natuurverhaal.natuurverhaal.exceptions.RecordNotFoundException;
import nl.natuurverhaal.natuurverhaal.models.Authority;
import nl.natuurverhaal.natuurverhaal.models.User;
import nl.natuurverhaal.natuurverhaal.repositories.UserRepository;
import nl.natuurverhaal.natuurverhaal.utils.RandomStringGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class UserService {

//    De constructor injecteert een instantie van UserRepository
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }


    public List<UserDto> getUsers() {
        List<UserDto> collection = new ArrayList<>();
        List<User> list = userRepository.findAll();
        for (User user : list) {
            collection.add(fromUser(user));
        }
        return collection;
    }

//    Haalt een specifieke gebruiker op basis van de gebruikersnaam en converteert deze naar een UserDto
    public UserDto getUser(String username) {
        UserDto dto = new UserDto();
        Optional<User> user = userRepository.findById(username);
        if (user.isPresent()){
            dto = fromUser(user.get());
        }else {
            throw new UsernameNotFoundException(username);
        }
        return dto;
    }

//    Controleert of een gebruiker met de opgegeven gebruikersnaam bestaat.
    public boolean userExists(String username) {
        return userRepository.existsById(username);
    }

//    Genereert een willekeurige API-sleutel, stelt deze in op de UserDto en slaat een nieuwe gebruiker op in de database. Geeft de gebruikersnaam van de aangemaakte gebruiker terug.
    public String createUser(UserDto userDto) {
        String randomString = RandomStringGenerator.generateAlphaNumeric(20);
        userDto.setApikey(randomString);
        User newUser = userRepository.save(toUser(userDto));
        return newUser.getUsername();
    }

//    Verwijdert een gebruiker op basis van de gebruikersnaam.
    public void deleteUser(String username) {
        userRepository.deleteById(username);
    }

//    Update het wachtwoord van een gebruiker.
    public void updateUser(String username, UserDto newUser) {
        if (!userRepository.existsById(username)) throw new RecordNotFoundException();
        User user = userRepository.findById(username).get();
        user.setPassword(newUser.getPassword());
        userRepository.save(user);
    }

//    Haalt de rollen (authorities) van een gebruiker op basis van de gebruikersnaam. Gooit een
    public Set<Authority> getAuthorities(String username) {
        if (!userRepository.existsById(username)) throw new UsernameNotFoundException(username);
        User user = userRepository.findById(username).get();
        UserDto userDto = fromUser(user);
        return userDto.getAuthorities();
    }

//    Voegt een nieuwe autoriteit (rol) toe aan een gebruiker.
    public void addAuthority(String username, String authority) {

        if (!userRepository.existsById(username)) throw new UsernameNotFoundException(username);
        User user = userRepository.findById(username).get();
        user.addAuthority(new Authority(username, authority));
        userRepository.save(user);
    }

//    Verwijdert een autoriteit van een gebruiker op basis van de gebruikersnaam en autoriteit.
    public void removeAuthority(String username, String authority) {
        if (!userRepository.existsById(username)) throw new UsernameNotFoundException(username);
        User user = userRepository.findById(username).get();
        Authority authorityToRemove = user.getAuthorities().stream().filter((a) -> a.getAuthority().equalsIgnoreCase(authority)).findAny().get();
        user.removeAuthority(authorityToRemove);
        userRepository.save(user);
    }

//    Zet een User-object om naar een UserDto
    public static UserDto fromUser(User user){

        var dto = new UserDto();

//        In de code lijkt getUsername() de
//        gebruikersnaam terug te geven, die vaak wordt gebruikt als een unieke
//        identificator voor een gebruiker.

        dto.username = user.getUsername();
        dto.password = user.getPassword();
        dto.enabled = user.isEnabled();
        dto.apikey = user.getApikey();
        dto.email = user.getEmail();
        dto.authorities = user.getAuthorities();

        return dto;
    }

//    Zet een UserDto-object om naar een User.
    public User toUser(UserDto userDto) {

        var user = new User();

        user.setUsername(userDto.getUsername());
        user.setPassword(userDto.getPassword());
        user.setEnabled(userDto.getEnabled());
        user.setApikey(userDto.getApikey());
        user.setEmail(userDto.getEmail());

        return user;
    }

}
