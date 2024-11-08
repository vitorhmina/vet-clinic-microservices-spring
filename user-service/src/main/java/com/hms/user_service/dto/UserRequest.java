package com.hms.user_service.dto;

import com.hms.user_service.model.UserType;

public record UserRequest(
        String id,
        String firstName,
        String lastName,
        String email,
        String password,
        String phoneNumber,
        boolean deleted,
        UserType userType
) {
}
