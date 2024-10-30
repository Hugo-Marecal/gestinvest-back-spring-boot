package dev.gest.invest.services;

import dev.gest.invest.dto.RegisterUserDto;
import dev.gest.invest.model.User;
import dev.gest.invest.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthService {

    private final UserRepository userRepo;
    private final PasswordEncoder passwordEncoder;

    public AuthService(UserRepository userRepo, PasswordEncoder passwordEncoder) {
        this.userRepo = userRepo;
        this.passwordEncoder = passwordEncoder;
    }

    public User signup(RegisterUserDto input) {
        Optional<User> alreadyExistingUser = userRepo.findByEmail(input.getEmail());

        if (alreadyExistingUser.isPresent()) {
            throw new RuntimeException("Email already use");
        }

        User user = new User(input.getEmail(), passwordEncoder.encode(input.getPassword()));
        return userRepo.save(user);
    }
}
