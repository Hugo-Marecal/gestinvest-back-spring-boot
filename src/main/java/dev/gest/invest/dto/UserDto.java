package dev.gest.invest.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserDto {

    @NotBlank(message = "Email is required")
    @Email
    private String email;
    private String last_name;
    private String first_name;

    public UserDto(String email, String last_name, String first_name) {
        this.email = email;
        this.last_name = last_name;
        this.first_name = first_name;
    }
}
