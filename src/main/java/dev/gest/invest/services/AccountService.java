package dev.gest.invest.services;

import dev.gest.invest.dto.UpdateUserDto;
import dev.gest.invest.dto.UserDto;
import dev.gest.invest.model.User;
import dev.gest.invest.repository.UserRepository;
import jakarta.mail.MessagingException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AccountService {

    private final UserRepository userRepo;
    private final JwtService jwtService;
    private final EmailService emailService;

    public AccountService(UserRepository userRepo, JwtService jwtService, EmailService emailService) {
        this.userRepo = userRepo;
        this.jwtService = jwtService;
        this.emailService = emailService;
    }

    public UserDto updateAccountInfos(User user, UpdateUserDto input) {

        User userData = userRepo.findByEmail(user.getEmail())
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        if (userData.getFirst_name() == null || !userData.getFirst_name().equalsIgnoreCase(input.getFirst_name())) {
            userData.setFirst_name(input.getFirst_name());
        }

        if (userData.getLast_name() == null || !userData.getLast_name().equalsIgnoreCase(input.getLast_name())) {
            userData.setLast_name(input.getLast_name());
        }

        User updateUser = userRepo.save(userData);

        return new UserDto(updateUser.getEmail(), updateUser.getLast_name(), updateUser.getFirst_name());
    }

    public UserDto editMail(User user, UpdateUserDto input) {
        Optional<User> alreadyExistingUser = userRepo.findByEmail(input.getEmail());

        if (alreadyExistingUser.isPresent()) {
            throw new IllegalArgumentException("Email already use");
        }

        User userData = userRepo.findByEmail(user.getEmail())
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        String token = jwtService.generateToken(input.getEmail());

        userData.setToken(token);

        User updatedUser = userRepo.save(userData);

        sendVerificationEmail(input.getEmail(), token);

        return new UserDto(updatedUser.getEmail());
    }

    public void sendVerificationEmail(String email, String token) {
        String subject = "Email verification";
        String htmlMessage = "<html><body>"
                + "<a href=\"http://localhost:8080/api/account/verify-mail/" + token + "\">Click here to verify your new email</a>"
                + "</body></html>";
        try {
            emailService.sendVerificationEmail(email, subject, htmlMessage);
            System.out.println("Email send");
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }

    public boolean verify(String token) {
        String userNewEmail = jwtService.extractUsername(token);
        if (userNewEmail == null) {
            throw new IllegalArgumentException("Invalid token");
        }

        User user = userRepo.findByToken(token)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        if (userNewEmail.equalsIgnoreCase(user.getEmail())) {
            return false;
        }

        if (!token.equalsIgnoreCase(user.getToken())) {
            throw new IllegalArgumentException("Invalid token");
        }

        user.setEmail(userNewEmail);
        userRepo.save(user);

        return true;
    }
}
