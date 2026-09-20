package com.graduation.letter.model.guest;

import jakarta.validation.constraints.NotBlank;

import java.util.Map;

public record GuestRequest(
        String name,

        @NotBlank(message = "Phone number is required")
        String phoneNumber,

        String type,

        Map<String, String> additionalInfo
) {
}
