package dev.gest.invest.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateUserDto {

    private String email;
    private String last_name;
    private String first_name;
    private String password;
}
