package com.graduation.letter.common;

import java.util.UUID;

public class Formatter {
    static public String normalizePhoneNumber(String phoneNumber) {
        if (phoneNumber == null) {
            return null;
        }
        // Remove all non-digit characters
        String normalized = phoneNumber.replaceAll("\\D", "");
        // If the number starts with '0', replace it with '+84'
        if (normalized.startsWith("0")) {
            normalized = "+84" + normalized.substring(1);
        } else if (!normalized.startsWith("+")) {
            // If it doesn't start with '+', assume it's a local number and add '+84'
            normalized = "+84" + normalized;
        }
        return normalized;
    }

    static public UUID parseUUID(String uuidString) {
        try {
            return UUID.fromString(uuidString);
        } catch (IllegalArgumentException e) {
            throw new RuntimeException("Invalid UUID string: " + uuidString, e);
        }
    }
}
