package dev.gest.invest.services;

import dev.gest.invest.dto.LoginUserDto;
import dev.gest.invest.dto.RegisterUserDto;
import dev.gest.invest.model.Portfolio;
import dev.gest.invest.model.User;
import dev.gest.invest.repository.PortfolioRepository;
import dev.gest.invest.repository.UserRepository;
import jakarta.mail.MessagingException;
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
    private final JwtService jwtService;
    private final PortfolioRepository portfolioRepository;
    private final EmailService emailService;

    public AuthService(
            UserRepository userRepo,
            PasswordEncoder passwordEncoder,
            AuthenticationManager authenticationManager,
            JwtService jwtService,
            PortfolioRepository portfolioRepository,
            EmailService emailService
    ) {
        this.userRepo = userRepo;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
        this.portfolioRepository = portfolioRepository;
        this.emailService = emailService;
    }

    public User signup(RegisterUserDto input) {
        Optional<User> alreadyExistingUser = userRepo.findByEmail(input.getEmail());

        if (alreadyExistingUser.isPresent()) {
            throw new RuntimeException("Email already use");
        }

        String token = jwtService.generateToken(input.getEmail());

        User user = new User(input.getEmail(), passwordEncoder.encode(input.getPassword()), token);

        User createdUser = userRepo.save(user);
        if (createdUser.getId() == null) {
            throw new RuntimeException("Error, creation failed");
        }

        sendVerificationEmail(createdUser, token);

        Portfolio newPortfolio = new Portfolio(createdUser.getId());
        portfolioRepository.save(newPortfolio);


        return createdUser;
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

    public void sendVerificationEmail(User user, String token) {
        String subject = "Account verification";
        String htmlMessage = "<html><body>"
                + "<a href=\"http://localhost:8080/api/auth/verify/" + token + "\">Click here to verify your email</a>"
                + "</body></html>";
        try {
            emailService.sendVerificationEmail(user.getEmail(), subject, htmlMessage);
            System.out.println("Email send");
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }
}
