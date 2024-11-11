package dev.gest.invest.controller;

import dev.gest.invest.dto.LoginUserDto;
import dev.gest.invest.dto.RegisterUserDto;
import dev.gest.invest.model.User;
import dev.gest.invest.responses.ApiResponse;
import dev.gest.invest.services.*;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;


@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;
    private final JwtService jwtService;
    private final GroupSymbolsService groupSymbolsService;
    private final CryptoPriceService cryptoPriceService;
    private final StockPriceService stockPriceService;

    public AuthController(AuthService authService, JwtService jwtService, CryptoPriceService cryptoPriceService, StockPriceService stockPriceService, GroupSymbolsService groupSymbolsService) {
        this.authService = authService;
        this.jwtService = jwtService;
        this.cryptoPriceService = cryptoPriceService;
        this.stockPriceService = stockPriceService;
        this.groupSymbolsService = groupSymbolsService;
    }

    @GetMapping("/")
    // method to test if method for cron is working
    public Mono<Map<String, Double>> welcome() {
//        return cryptoPriceService.updateCryptoPrices(1, 60);
        List<String> groups = groupSymbolsService.getSymbolsInGroups(2, 40);
        return stockPriceService.fetchPricesForGroupAsync(groups);
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
        String successMessage = URLEncoder.encode("Email successfully verified, please login now.", StandardCharsets.UTF_8);
        String errorMessage = URLEncoder.encode("Email already verified, please login now.", StandardCharsets.UTF_8);

        if (isVerified) {
            response.sendRedirect("http://localhost:5173/?successMessage=" + successMessage);
        } else {
            response.sendRedirect("http://localhost:5173/?errorMessage=" + errorMessage);
        }
    }

}