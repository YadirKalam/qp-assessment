package com.example.grocerybookingapi.service;

import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.grocerybookingapi.model.User;
import com.example.grocerybookingapi.repository.UserRepository;
import com.example.grocerybookingapi.security.JwtUtil;
import com.example.grocerybookingapi.exception.AuthenticationException;

@Service
public class AuthService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtUtil jwtUtil;

    private static final Logger LOGGER = Logger.getLogger(AuthService.class.getName());

    public User registerUser(User user) {
        try {
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            userRepository.save(user);
            return user;
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error registering user: " + e.getMessage(), e);
            throw new RuntimeException("Failed to register user");
        }
    }

    public String login(String email, String password) {
        try {
            Optional<User> userOptional = userRepository.findByEmail(email);
            if (userOptional.isPresent() && passwordEncoder.matches(password, userOptional.get().getPassword())) {
                return jwtUtil.generateToken(email);
            } else {
                throw new AuthenticationException("Invalid email or password");
            }
        } catch (AuthenticationException e) {
            LOGGER.log(Level.WARNING, e.getMessage(), e);
            throw e;
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error during login: " + e.getMessage(), e);
            throw new RuntimeException("Failed to authenticate user");
        }
    }
}
