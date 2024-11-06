package com.hms.user_service.service;

import com.hms.user_service.dto.AuthRequest;
import com.hms.user_service.dto.AuthResponse;
import com.hms.user_service.exceptions.EmailNotFoundException;
import com.hms.user_service.exceptions.PasswordMismatchException;
import com.hms.user_service.model.User;
import com.hms.user_service.repository.UserRepository;
import com.hms.user_service.utils.JwtUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder(10, new SecureRandom());

    private final JwtUtil jwtUtil;

    public AuthResponse login(AuthRequest authRequest) {

        Optional<User> optionalUser = userRepository.findByEmail(authRequest.email());

        User user = optionalUser.orElseThrow(() -> new EmailNotFoundException("User not found with email: " + authRequest.email()));

        if (!passwordEncoder.matches(authRequest.password(), user.getPassword())) {
            throw new PasswordMismatchException("Password does not match");
        }

        Map<String, Object> claims = Map.of(
                "email", user.getEmail(),
                "role", user.getUserType()
        );

        String token = jwtUtil.createToken(claims, user.getId());

        return new AuthResponse(user.getEmail(), token);
    }
}
