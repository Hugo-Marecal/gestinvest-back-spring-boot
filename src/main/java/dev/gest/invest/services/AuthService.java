package dev.gest.invest.services;

import dev.gest.invest.dto.LoginUserDto;
import dev.gest.invest.dto.RegisterUserDto;
import dev.gest.invest.dto.ResetPasswordDto;
import dev.gest.invest.dto.UpdateUserDto;
import dev.gest.invest.model.Portfolio;
import dev.gest.invest.model.User;
import dev.gest.invest.repository.PortfolioRepository;
import dev.gest.invest.repository.UserRepository;
import jakarta.mail.MessagingException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.concurrent.TimeUnit;

@Service
public class AuthService {

    @Value("${client.url}")
    private String clientUrl;

    @Value("${api.url}")
    private String apiUrl;

    private final UserRepository userRepo;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final PortfolioRepository portfolioRepository;
    private final UserDetailsService userDetailsService;
    private final EmailService emailService;
    private final JwtService jwtService;

    public AuthService(
            UserRepository userRepo,
            PasswordEncoder passwordEncoder,
            AuthenticationManager authenticationManager,
            PortfolioRepository portfolioRepository,
            UserDetailsService userDetailsService,
            EmailService emailService,
            JwtService jwtService
    ) {
        this.userRepo = userRepo;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
        this.portfolioRepository = portfolioRepository;
        this.emailService = emailService;
        this.userDetailsService = userDetailsService;
    }

    public User signup(RegisterUserDto input) {
        Optional<User> alreadyExistingUser = userRepo.findByEmail(input.getEmail());

        if (alreadyExistingUser.isPresent()) {
            throw new IllegalArgumentException("Email déjà utilisé");
        }

        // 1 hour to milliseconds
        long expirationTime = TimeUnit.HOURS.toMillis(1);

        String token = jwtService.generateToken(input.getEmail(), expirationTime);

        User user = new User(input.getEmail(), passwordEncoder.encode(input.getPassword()), token);

        User createdUser = userRepo.save(user);
        if (createdUser.getId() == null) {
            throw new IllegalArgumentException("Erreur, la création a échoué");
        }

        sendVerificationEmail(createdUser, token);

        Portfolio newPortfolio = new Portfolio(createdUser.getId());
        portfolioRepository.save(newPortfolio);


        return createdUser;
    }

    public User authenticate(LoginUserDto input) {
        User user = userRepo.findByEmail(input.getEmail())
                .orElseThrow(() -> new IllegalArgumentException("Échec de l'authentification"));

        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        input.getEmail(),
                        input.getPassword()
                )
        );

        if (!user.getVerified()) {
            throw new IllegalArgumentException("Compte non vérifié. Veuillez vérifier votre compte");
        }

        return user;
    }

    public void sendVerificationEmail(User user, String token) {
        String subject = "Account verification";
        String htmlMessage = "<html><body>"
                + "<a href=" + apiUrl + "/api/auth/verify/" + token + ">Click here to verify your email</a>"
                + "</body></html>";
        try {
            emailService.sendVerificationEmail(user.getEmail(), subject, htmlMessage);
            System.out.println("Email envoyé");
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }

    public boolean verify(String token) {
        String userEmail = jwtService.extractUsername(token);
        if (userEmail == null) {
            throw new IllegalArgumentException("Invalide token");
        }

        UserDetails userDetails = userDetailsService.loadUserByUsername(userEmail);

        boolean isTokenValid = jwtService.isTokenValid(token, userDetails);
        if (!isTokenValid) {
            throw new IllegalArgumentException("Invalide token");
        }

        User user = userRepo.findByEmail(userEmail)
                .orElseThrow(() -> new IllegalArgumentException("Utilisateur non trouvé"));

        if (user.getVerified()) {
            return false;
        }

        user.setVerified(true);
        user.setToken(null);
        userRepo.save(user);

        return true;
    }

    public void forgotPassword(UpdateUserDto input) {
        User user = userRepo.findByEmail(input.getEmail())
                .orElseThrow(() -> new IllegalArgumentException("Si un compte est associé à cet email, un email sera envoyé pour réinitialiser le mot de passe."));

        // 1 hour to milliseconds
        long expirationTime = TimeUnit.HOURS.toMillis(1);

        String token = jwtService.generateToken(input.getEmail(), expirationTime);

        user.setToken(token);

        User updatedUser = userRepo.save(user);

        sendVerificationEmail(input.getEmail(), token);
    }

    public void sendVerificationEmail(String email, String token) {
        String subject = "Reset password";
        String htmlMessage = "<html><body>"
                + "<a href=" + clientUrl + "/reset-password?token=" + token + ">Click here to reset your password</a>"
                + "</body></html>";
        try {
            emailService.sendVerificationEmail(email, subject, htmlMessage);
            System.out.println("Email send");
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }

    public void resetPassword(ResetPasswordDto input) {
        User user = userRepo.findByToken(input.getToken())
                .orElseThrow(() -> new IllegalArgumentException("Token invalide our expiré"));

        UserDetails userDetails = userDetailsService.loadUserByUsername(user.getEmail());

        boolean isTokenValid = jwtService.isTokenValid(input.getToken(), userDetails);
        if (!isTokenValid) {
            throw new IllegalArgumentException("Token invalide our expiré");
        }

        if (passwordEncoder.matches(input.getPassword(), user.getPassword())) {
            throw new IllegalArgumentException("Le nouveau mot de passe doit être différent de l'ancien mot de passe");
        }

        user.setPassword(passwordEncoder.encode(input.getPassword()));
        user.setToken(null);
        userRepo.save(user);
    }
}
