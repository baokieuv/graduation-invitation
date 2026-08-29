package com.graduation.letter.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum ErrorCode {
    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "SYS-500", "Internal server error"),
    VALIDATION_ERROR(HttpStatus.BAD_REQUEST, "SYS-400", "Validation error"),
    MALFORMED_REQUEST_BODY(HttpStatus.BAD_REQUEST, "SYS-401", "Malformed request body"),
    MISSING_REQUEST_PARAMETER(HttpStatus.BAD_REQUEST, "SYS-402", "Missing required parameter"),
    RESOURCE_NOT_FOUND(HttpStatus.NOT_FOUND, "RES-404", "Resource not found"),
    RESOURCE_ALREADY_EXISTS(HttpStatus.CONFLICT, "RES-409", "Resource already exists"),
    INVALID_FORMAT(HttpStatus.BAD_REQUEST, "SYS-403", "Invalid data format");

    private final HttpStatus httpStatus;
    private final String code;
    private final String message;

    ErrorCode(HttpStatus httpStatus, String code, String message) {
        this.httpStatus = httpStatus;
        this.code = code;
        this.message = message;
    }
}