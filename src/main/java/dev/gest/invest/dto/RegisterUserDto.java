package dev.gest.invest.dto;


import dev.gest.invest.validation.PasswordMatches;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@PasswordMatches(
        passwordField = "password",
        confirmationField = "confirmation"
)
public class RegisterUserDto {

    @NotBlank(message = "Email requis")
    @Email
    private String email;

    @NotBlank(message = "Mot de passe requis")
    @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$",
            message = "Le mot de passe doit contenir au moins une lettre minuscule, une lettre majuscule, un chiffre, un caractère spécial et comporter au moins 8 caractères.")
    private String password;

    @NotBlank(message = "Confirmation du mot de passe requise")
    private String confirmation;
}
