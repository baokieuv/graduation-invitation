package com.graduation.letter.common;

import java.util.UUID;

public final class Formatter {

    private Formatter() {
    }

    public static String normalizeIdentifier(String input) {
        if (input == null || input.trim().isEmpty()) {
            return null;
        }

        String trimmed = input.trim();

        // Kiểm tra xem đầu vào là chuỗi định dạng số (Phone / ID) hay là tên
        // Regex này match nếu chuỗi CHỈ chứa số và các ký tự: khoảng trắng, +, -, ., (, )
        boolean isNumericIdentifier = trimmed.matches("^[\\+\\-\\s\\(\\)\\.0-9]+$");

        if (!isNumericIdentifier) {
            // Trường hợp đầu vào là TÊN (có chứa chữ cái hoặc ký tự khác)
            // Chuẩn hoá: Thay thế các khoảng trắng liên tiếp thành một khoảng trắng duy nhất
            return trimmed.replaceAll("\\s+", " ");
        }

        // ==========================================
        // TRƯỜNG HỢP ĐẦU VÀO LÀ SỐ ĐIỆN THOẠI / HUST ID
        // ==========================================

        // Remove all non-digit characters (e.g., spaces, dashes, plus signs)
        String normalized = trimmed.replaceAll("\\D", "");

        if (normalized.isEmpty()) {
            throw new IllegalArgumentException("Invalid identifier format: " + input);
        }

        // 1. Check for HUST Student ID format (yyyyabcd)
        // It must be exactly 8 digits. We check if it starts with "19" or "20"
        // to prevent random 8-digit numbers from being falsely accepted.
        if (normalized.length() == 8 && (normalized.startsWith("19") || normalized.startsWith("20"))) {
            return normalized;
        }

        // 2. Otherwise, apply Phone Number normalization rules
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
            throw new IllegalArgumentException("Invalid phone number or HUST student ID format: " + input);
        }

        return normalized;
    }

    public static UUID parseUUID(String uuidString) {
        if (uuidString == null || uuidString.trim().isEmpty()) {
            throw new IllegalArgumentException("UUID string cannot be null or empty");
        }

        try {
            return UUID.fromString(uuidString);
        } catch (IllegalArgumentException e) {
            throw new RuntimeException("Invalid UUID string: " + uuidString, e);
        }
    }
}