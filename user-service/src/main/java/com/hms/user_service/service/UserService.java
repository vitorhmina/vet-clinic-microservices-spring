package com.hms.user_service.service;

import com.hms.user_service.dto.UserRequest;
import com.hms.user_service.model.User;
import com.hms.user_service.model.UserType;
import com.hms.user_service.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserService {

    private final UserRepository userRepository;
    BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder(10, new SecureRandom());

    public void createUser(UserRequest userRequest) {
        User user = createUserEntity(userRequest);
        user.setUserType(userRequest.userType());

        saveUser(user);
    }

    public void createClient(UserRequest userRequest) {
        User user = createUserEntity(userRequest);
        user.setUserType(UserType.CLIENT);

        saveUser(user);
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public Optional<User> getUserById(UUID userId) {
        return userRepository.findById(userId);
    }

    private User createUserEntity(UserRequest userRequest) {
        User user = new User();
        user.setFirstName(userRequest.firstName());
        user.setLastName(userRequest.lastName());
        user.setEmail(userRequest.email());
        String hashedPassword = passwordEncoder.encode(userRequest.password());
        user.setPassword(hashedPassword);
        user.setPhoneNumber(userRequest.phoneNumber());
        user.setDeleted(false);
        user.setCreatedAt(LocalDateTime.now());

        return user;
    }

    private void saveUser(User user) {
        try {
            userRepository.save(user);
        } catch (Exception e) {
            log.error("Error occurred while creating user: {}", e.getMessage());
            throw new RuntimeException("Failed to create user");
        }
    }
}
