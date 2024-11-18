package dev.gest.invest.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginUserDto {

    @NotBlank(message = "Email requis")
    @Email
    private String email;

    @NotBlank(message = "Mot de passe requis")
    private String password;
}
