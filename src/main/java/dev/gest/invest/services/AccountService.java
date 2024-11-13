package dev.gest.invest.services;

import dev.gest.invest.dto.UpdateUserDto;
import dev.gest.invest.dto.UserDto;
import dev.gest.invest.model.User;
import dev.gest.invest.repository.UserRepository;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AccountService {

    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;

    public AccountService(PasswordEncoder passwordEncoder, UserRepository userRepository) {
        this.passwordEncoder = passwordEncoder;
        this.userRepository = userRepository;
    }

    public UserDto updateAccountInfos(User user, UpdateUserDto updateUserDto) {
        if (!passwordEncoder.matches(updateUserDto.getPassword(), user.getPassword())) {
            throw new BadCredentialsException("Auth failed");
        }

        User userData = userRepository.findByEmail(user.getEmail())
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        if (userData.getFirst_name() == null || !userData.getFirst_name().equalsIgnoreCase(updateUserDto.getFirst_name())) {
            userData.setFirst_name(updateUserDto.getFirst_name());
        }

        if (userData.getLast_name() == null || !userData.getLast_name().equalsIgnoreCase(updateUserDto.getLast_name())) {
            userData.setLast_name(updateUserDto.getLast_name());
        }

        if (userData.getEmail() == null || !userData.getEmail().equalsIgnoreCase(updateUserDto.getEmail())) {
            userData.setEmail(updateUserDto.getEmail());
        }

        User updateUser = userRepository.save(userData);

        return new UserDto(updateUser.getEmail(), updateUser.getLast_name(), updateUser.getFirst_name());
    }
}
