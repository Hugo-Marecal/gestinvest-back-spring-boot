package dev.gest.invest.dto;

import dev.gest.invest.validation.PasswordMatches;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@PasswordMatches(
        passwordField = "newPassword",
        confirmationField = "confirmation"
)
public class EditPasswordDto {

    @NotBlank(message = "Mot de passe actuel requis")
    private String currentPassword;

    @NotBlank(message = "Nouveau mot de passe requis")
    @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$",
            message = "Le nouveau mot de passe doit contenir au moins une lettre minuscule, une lettre majuscule, un chiffre, un caractère spécial et comporter au moins 8 caractères.")
    private String newPassword;

    @NotBlank(message = "Confirmation du mot de passe requise")
    private String confirmation;
}
