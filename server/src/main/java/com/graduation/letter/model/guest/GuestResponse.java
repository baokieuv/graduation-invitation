package com.graduation.letter.model.guest;

import java.util.Map;

public record GuestResponse(
        String id,

        String name,

        String phoneNumber,

        String type,

        Map<String, String> additionalInfo

) {
    public GuestResponse(Guest guest) {
        this(
                guest.getId().toString(),
                guest.getName(),
                guest.getPhoneNumber(),
                guest.getType().name(),
                guest.getAdditionalInfo()
        );
    }
}
