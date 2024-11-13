package dev.gest.invest.controller;

import dev.gest.invest.dto.UserDto;
import dev.gest.invest.model.User;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/account")
public class AccountController {

    @GetMapping("/")
    public ResponseEntity<UserDto> getAccountInfos(@AuthenticationPrincipal User user) {
        UserDto userDto = new UserDto(user.getEmail());

        return ResponseEntity.ok(userDto);
    }
}
