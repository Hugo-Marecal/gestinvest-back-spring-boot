package dev.gest.invest.dto;


import dev.gest.invest.validation.PasswordMatches;
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
public class ResetPasswordDto {

    @NotBlank(message = "Password is required")
    @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$",
            message = "Password must contain at least one lowercase letter, one uppercase letter, one digit, one special character, and be at least 8 characters long")
    private String password;

    @NotBlank(message = "Confirmation is required")
    private String confirmation;

    private String token;
}
