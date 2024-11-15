package dev.gest.invest.controller;

import dev.gest.invest.dto.UpdateUserDto;
import dev.gest.invest.dto.UserDto;
import dev.gest.invest.model.User;
import dev.gest.invest.services.AccountService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/account")
public class AccountController {

    private final AccountService accountService;

    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    @GetMapping("/")
    public ResponseEntity<UserDto> getAccountInfos(@AuthenticationPrincipal User user) {
        UserDto userInfos = new UserDto(user.getEmail(), user.getLast_name(), user.getFirst_name());

        return ResponseEntity.ok(userInfos);
    }

    @PatchMapping("/")
    public ResponseEntity<UserDto> updateAccountInfos(@AuthenticationPrincipal User user, @RequestBody UpdateUserDto updateUserDto) {
        UserDto updatedUser = accountService.updateAccountInfos(user, updateUserDto);

        return ResponseEntity.ok(updatedUser);
    }

    @PatchMapping("/edit-mail")
    public ResponseEntity<UserDto> editMail(@AuthenticationPrincipal User user, @RequestBody UpdateUserDto updateUserDto) {
        UserDto updatedUser = accountService.editMail(user, updateUserDto);

        return ResponseEntity.ok(updatedUser);
    }
}
