package dev.gest.invest.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserDto {

    @NotBlank(message = "Email requis")
    @Email
    private String email;
    private String last_name;
    private String first_name;

    public UserDto(String email) {
        this.email = email;
    }

    public UserDto(String email, String last_name, String first_name) {
        this.email = email;
        this.last_name = last_name;
        this.first_name = first_name;
    }
}
