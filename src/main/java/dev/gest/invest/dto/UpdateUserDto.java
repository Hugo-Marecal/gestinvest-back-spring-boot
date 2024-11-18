package dev.gest.invest.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateUserDto {

    @NotBlank(message = "Email requis")
    @Email
    private String email;
    private String last_name;
    private String first_name;
    private String password;
}
