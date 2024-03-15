package nl.natuurverhaal.natuurverhaal.services;

import nl.natuurverhaal.natuurverhaal.dtos.UserDto;
import nl.natuurverhaal.natuurverhaal.models.Authority;
import nl.natuurverhaal.natuurverhaal.models.User;
import nl.natuurverhaal.natuurverhaal.repositories.UserRepository;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Set;

import static nl.natuurverhaal.natuurverhaal.services.UserService.fromUser;

@Service
public class AuthorityService {

    UserRepository userRepository;

    public AuthorityService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

public Set<Authority> getAuthorities(String username) {
    if (!userRepository.existsById(username)) throw new UsernameNotFoundException(username);
    User user = userRepository.findById(username).get();
    UserDto userDto = fromUser(user);
    return userDto.getAuthorities();
}

public void addAuthority(String username, String authority) {

    if (!userRepository.existsById(username)) throw new UsernameNotFoundException(username);
    User user = userRepository.findById(username).get();
    user.addAuthority(new Authority(username, authority));
    userRepository.save(user);
}

public void removeAuthority(String username, String authority) {
    if (!userRepository.existsById(username)) throw new UsernameNotFoundException(username);
    User user = userRepository.findById(username).get();
    Authority authorityToRemove = user.getAuthorities().stream().filter((a) -> a.getAuthority().equalsIgnoreCase(authority)).findAny().get();
    user.removeAuthority(authorityToRemove);
    userRepository.save(user);
}
}