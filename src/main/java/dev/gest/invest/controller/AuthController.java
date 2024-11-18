package dev.gest.invest.controller;

import dev.gest.invest.dto.LoginUserDto;
import dev.gest.invest.dto.RegisterUserDto;
import dev.gest.invest.dto.UpdateUserDto;
import dev.gest.invest.model.User;
import dev.gest.invest.repository.UserRepository;
import dev.gest.invest.responses.ApiResponse;
import dev.gest.invest.services.AuthService;
import dev.gest.invest.services.JwtService;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.TimeUnit;


@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Value("${client.url}")
    private String clientUrl;

    private final AuthService authService;
    private final JwtService jwtService;
    private final UserDetailsService userDetailsService;
    private final UserRepository userRepo;


    public AuthController(AuthService authService, JwtService jwtService, UserDetailsService userDetailsService, UserRepository userRepo) {
        this.authService = authService;
        this.jwtService = jwtService;
        this.userDetailsService = userDetailsService;
        this.userRepo = userRepo;
    }

    @GetMapping("/")
    public String welcome() {
        return "Hello";
    }

    @PostMapping("/signup")
    public ResponseEntity<ApiResponse> register(@Valid @RequestBody RegisterUserDto registerUserDto) {
        authService.signup(registerUserDto);

        ApiResponse response = new ApiResponse("success", "User registered successfully, please verify your email");
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping("/login")
    public ResponseEntity<ApiResponse> login(@Valid @RequestBody LoginUserDto loginUserDto) {
        User authenticatedUser = authService.authenticate(loginUserDto);

        // 1 day to milliseconds
        long expirationTime = TimeUnit.DAYS.toMillis(1);

        String jwtToken = jwtService.generateToken(authenticatedUser.getUsername(), expirationTime);

        ApiResponse response = new ApiResponse("success", "User successfully logged in", jwtToken);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/verify/{token}")
    public void verifyEmail(@PathVariable String token, HttpServletResponse response) throws IOException {
        boolean isVerified = authService.verify(token);

        //Encode message for client can read it and display it
        String successMessage = URLEncoder.encode("Email successfully verified, please login now.", StandardCharsets.UTF_8);
        String errorMessage = URLEncoder.encode("Email already verified, please login now.", StandardCharsets.UTF_8);

        if (isVerified) {
            response.sendRedirect(clientUrl + "/?successMessage=" + successMessage);
        } else {
            response.sendRedirect(clientUrl + "/?errorMessage=" + errorMessage);
        }
    }

    @PostMapping("/forgot-password")
    public ResponseEntity<ApiResponse> forgotPassword(@RequestBody @Valid UpdateUserDto input) {
        authService.forgotPassword(input);

        ApiResponse response = new ApiResponse("success", "If an account is associated with this email, an email will be sent to reset the password.");
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/reset-password-verify-token")
    public ResponseEntity<ApiResponse> resetPassword(@RequestParam String token) {
        User user = userRepo.findByToken(token)
                .orElseThrow(() -> new IllegalArgumentException("Invalid or expired token"));

        UserDetails userDetails = userDetailsService.loadUserByUsername(user.getEmail());

        boolean isTokenValid = jwtService.isTokenValid(token, userDetails);
        System.out.println(isTokenValid);
        if (!isTokenValid) {
            throw new IllegalArgumentException("Invalid or expired token");
        }

        ApiResponse response = new ApiResponse("success", "Token valid");
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}