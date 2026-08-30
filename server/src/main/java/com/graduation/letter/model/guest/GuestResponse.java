package com.graduation.letter.model.guest;

import java.util.Map;

public record GuestResponse(
        String id,

        String name,

        String phoneNumber,

        Map<String, String> additionalInfo
) {
}
