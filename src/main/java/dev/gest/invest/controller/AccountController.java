package dev.gest.invest.controller;

import dev.gest.invest.dto.EditPasswordDto;
import dev.gest.invest.dto.UpdateUserDto;
import dev.gest.invest.dto.UserDto;
import dev.gest.invest.model.User;
import dev.gest.invest.responses.ApiResponse;
import dev.gest.invest.services.AccountService;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

@RestController
@RequestMapping("/api/account")
public class AccountController {

    @Value("${client.url}")
    private String clientUrl;

    private final AccountService accountService;

    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    @GetMapping("/")
    public ResponseEntity<UserDto> getAccountInfos(@AuthenticationPrincipal User user) {
        UserDto userInfos = new UserDto(user.getEmail(), user.getLast_name(), user.getFirst_name());

        return ResponseEntity.ok(userInfos);
    }

    @PatchMapping("/")
    public ResponseEntity<UserDto> updateAccountInfos(@AuthenticationPrincipal User user, @RequestBody UpdateUserDto updateUserDto) {
        UserDto updatedUser = accountService.updateAccountInfos(user, updateUserDto);

        return ResponseEntity.ok(updatedUser);
    }

    @PatchMapping("/edit-mail")
    public ResponseEntity<ApiResponse> editMail(@AuthenticationPrincipal User user, @RequestBody @Valid UpdateUserDto updateUserDto) {
        UserDto updatedUser = accountService.editMail(user, updateUserDto);

        ApiResponse response = new ApiResponse("success", "Envoi de la demande, veuillez vérifier votre nouvel email");
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/verify-mail/{token}")
    public void verifyMail(@PathVariable String token, HttpServletResponse response) throws IOException {
        boolean isVerified = accountService.verify(token);

        //Encode message for client can read it and display it
        String successMessage = URLEncoder.encode("L'e-mail a été modifié avec succès, veuillez vous connecter avec votre nouvel e-mail.", StandardCharsets.UTF_8);
        String errorMessage = URLEncoder.encode("L'e-mail a déjà été modifiée, veuillez vous connecter avec votre nouvelle adresse.", StandardCharsets.UTF_8);

        if (isVerified) {
            response.sendRedirect(clientUrl + "/?clearLocalStorage=true&successMessage=" + successMessage);
        } else {
            response.sendRedirect(clientUrl + "/?clearLocalStorage=true&errorMessage=" + errorMessage);
        }
    }

    @PatchMapping("/edit-password")
    public ResponseEntity<ApiResponse> editPassword(@AuthenticationPrincipal User user, @RequestBody @Valid EditPasswordDto editPasswordDto) {
        accountService.editPassword(user, editPasswordDto);

        ApiResponse response = new ApiResponse("success", "Mot de passe mis à jour avec succès");
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PatchMapping("/delete")
    public ResponseEntity<ApiResponse> deletedAccount(@AuthenticationPrincipal User user) {
        accountService.deleteAccount(user);

        ApiResponse response = new ApiResponse("success", "Compte supprimé");
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
