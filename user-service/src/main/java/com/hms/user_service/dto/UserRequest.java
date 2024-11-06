package com.hms.user_service.dto;

import com.hms.user_service.model.UserType;

import java.util.Date;
import java.util.UUID;

public record UserRequest(
        UUID id,
        String firstName,
        String lastName,
        String email,
        String password,
        String phoneNumber,
        boolean deleted,
        UserType userType
) {
}
