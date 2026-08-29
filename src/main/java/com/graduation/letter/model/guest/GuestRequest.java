package com.graduation.letter.model.guest;

import jakarta.validation.constraints.NotBlank;

public record GuestRequest(
        String name,

        @NotBlank(message = "Phone number is required")
        String phoneNumber
) {
}
