package dev.gest.invest.responses;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ApiResponse {

    private String status;
    private String message;
    private String token;

    public ApiResponse(String status, String message) {
        this.status = status;
        this.message = message;
    }

    public ApiResponse(String status, String message, String token) {
        this.status = status;
        this.message = message;
        this.token = token;
    }
}
