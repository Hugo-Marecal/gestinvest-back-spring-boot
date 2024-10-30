package dev.gest.invest.services;

import dev.gest.invest.model.User;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    public User signup(User input) {
        System.out.println(input.getUsername());
        return new User();
    }
}
