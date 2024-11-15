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

    @NotBlank(message = "Current Password is required")
    private String currentPassword;

    @NotBlank(message = "New Password is required")
    @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$",
            message = "New Password must contain at least one lowercase letter, one uppercase letter, one digit, one special character, and be at least 8 characters long")
    private String newPassword;

    @NotBlank(message = "Confirmation is required")
    private String confirmation;
}
