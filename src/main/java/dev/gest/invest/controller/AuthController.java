package dev.gest.invest.controller;

import dev.gest.invest.model.User;
import dev.gest.invest.responses.ApiResponse;
import dev.gest.invest.services.AuthService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @GetMapping("/")
    public String welcome() {
        return "Welcome from the API";
    }

    @PostMapping("/signup")
    public ResponseEntity<ApiResponse> register(@RequestBody User user) {
        try {

            User registeredUser = authService.signup(user);

            ApiResponse response = new ApiResponse("success", "User registered successfully");
            return new ResponseEntity<>(response, HttpStatus.OK);

        } catch (Exception e) {
            ApiResponse response = new ApiResponse("error", "User registration failed: " + e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
    }
}
