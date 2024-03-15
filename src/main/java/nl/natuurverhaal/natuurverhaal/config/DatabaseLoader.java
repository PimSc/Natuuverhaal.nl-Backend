package nl.natuurverhaal.natuurverhaal.config;

import nl.natuurverhaal.natuurverhaal.models.User;
import nl.natuurverhaal.natuurverhaal.models.Authority;
import nl.natuurverhaal.natuurverhaal.repositories.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

    @Configuration
    public class DatabaseLoader {

        @Bean
        CommandLineRunner initDatabase(UserRepository userRepository, PasswordEncoder passwordEncoder) {
            return args -> {
                if (userRepository.findByUsername("admin").isEmpty()) {
                    User admin = new User();
                    admin.setUsername("Testadmin");
                    admin.setPassword(passwordEncoder.encode("12345678%"));

                    Authority adminAuthority = new Authority();
                    adminAuthority.setAuthority("ROLE_ADMIN");
                    adminAuthority.setUsername(admin.getUsername());
                    admin.getAuthorities().add(adminAuthority);

                    userRepository.save(admin);
                }
                if (userRepository.findByUsername("user").isEmpty()) {
                    User user = new User();
                    user.setUsername("Testgebruiker");
                    user.setPassword(passwordEncoder.encode("12345678%"));

                    Authority adminAuthority = new Authority();
                    adminAuthority.setAuthority("ROLE_USER");
                    adminAuthority.setUsername(user.getUsername());
                    user.getAuthorities().add(adminAuthority);

                    userRepository.save(user);
                }
            };
        }
    }

