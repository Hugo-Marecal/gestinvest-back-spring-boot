package dev.gest.invest.services;

import dev.gest.invest.dto.LoginUserDto;
import dev.gest.invest.dto.RegisterUserDto;
import dev.gest.invest.model.User;
import dev.gest.invest.repository.UserRepository;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthService {

    private final UserRepository userRepo;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;

    public AuthService(UserRepository userRepo, PasswordEncoder passwordEncoder, AuthenticationManager authenticationManager) {
        this.userRepo = userRepo;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
    }

    public User signup(RegisterUserDto input) {
        Optional<User> alreadyExistingUser = userRepo.findByEmail(input.getEmail());

        if (alreadyExistingUser.isPresent()) {
            throw new RuntimeException("Email already use");
        }

        User user = new User(input.getEmail(), passwordEncoder.encode(input.getPassword()));
        return userRepo.save(user);
    }

    public User authenticate(LoginUserDto input) {
        try {
            User user = userRepo.findByEmail(input.getEmail())
                    .orElseThrow(() -> new RuntimeException("Authentication failed"));

            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            input.getEmail(),
                            input.getPassword()
                    )
            );

            if (!user.getVerified()) {
                throw new RuntimeException("Account not verified. Please verify your account");
            }

            return user;
        } catch (AuthenticationException e) {
            throw new RuntimeException("Authentication failed");
        }
    }
}
