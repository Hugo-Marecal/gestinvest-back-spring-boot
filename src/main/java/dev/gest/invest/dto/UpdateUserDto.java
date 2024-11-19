package dev.gest.invest.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateUserDto {

    @NotBlank(message = "Email requis")
    @Email
    private String email;

    @Size(max = 25, message = "Le nom ne doit pas dépasser 25 caractères.")
    private String last_name;

    @Size(max = 25, message = "Le nom ne doit pas dépasser 25 caractères.")
    private String first_name;
}
