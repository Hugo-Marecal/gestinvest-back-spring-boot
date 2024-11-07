package dev.gest.invest.controller;

import dev.gest.invest.dto.LoginUserDto;
import dev.gest.invest.dto.RegisterUserDto;
import dev.gest.invest.model.User;
import dev.gest.invest.responses.ApiResponse;
import dev.gest.invest.services.AuthService;
import dev.gest.invest.services.JwtService;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;


@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;
    private final JwtService jwtService;

    public AuthController(AuthService authService, JwtService jwtService) {
        this.authService = authService;
        this.jwtService = jwtService;
    }

    @GetMapping("/")
    public String welcome() {
        return "Welcome from the API";
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

        String jwtToken = jwtService.generateToken(authenticatedUser.getUsername());

        ApiResponse response = new ApiResponse("success", "User successfully logged in", jwtToken);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/verify/{token}")
    public void verifyEmail(@PathVariable String token, HttpServletResponse response) throws IOException {
        boolean isVerified = authService.verify(token);

        //Encode message for client can read it and display it
        String successMessage = URLEncoder.encode("Email vérifié avec succès, veuillez maintenant vous connecter.", StandardCharsets.UTF_8);
        String errorMessage = URLEncoder.encode("Email déjà vérifié, veuillez maintenant vous connecter.", StandardCharsets.UTF_8);

        if (isVerified) {
            response.sendRedirect("http://localhost:5173/?successMessage=" + successMessage);
        } else {
            response.sendRedirect("http://localhost:5173/?errorMessage=" + errorMessage);
        }
    }

}