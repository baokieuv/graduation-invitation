package com.graduation.letter.common;

import java.util.UUID;

public final class Formatter {

    private Formatter() {
    }

    public static String normalizePhoneNumber(String phoneNumber) {
        if (phoneNumber == null || phoneNumber.trim().isEmpty()) {
            return null;
        }

        // Remove all non-digit characters (e.g., spaces, dashes, plus signs)
        String normalized = phoneNumber.replaceAll("\\D", "");

        // If the number starts with the country code '84', replace it with '0'
        if (normalized.startsWith("84")) {
            normalized = "0" + normalized.substring(2);
        }
        // If the length is 9 and it doesn't start with '0', prepend '0'
        else if (!normalized.startsWith("0") && normalized.length() == 9) {
            normalized = "0" + normalized;
        }

        // Validate: Ensure the final string is exactly 10 characters and starts with '0'
        if (normalized.length() != 10 || !normalized.startsWith("0")) {
            throw new IllegalArgumentException("Invalid phone number format: " + phoneNumber);
        }

        return normalized;
    }

    public static UUID parseUUID(String uuidString) {
        try {
            return UUID.fromString(uuidString);
        } catch (IllegalArgumentException e) {
            throw new RuntimeException("Invalid UUID string: " + uuidString, e);
        }
    }
}