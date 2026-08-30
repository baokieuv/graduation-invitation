package com.graduation.letter.common;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum SuccessCode {
    OK(HttpStatus.OK, "SYS-200", "Success"),
    CREATED(HttpStatus.CREATED, "SYS-201", "Created successfully"),
    ACCEPTED(HttpStatus.ACCEPTED, "SYS-202", "Request accepted"),
    NO_CONTENT(HttpStatus.NO_CONTENT, "SYS-204", "No content");

    private final HttpStatus httpStatus;
    private final String code;
    private final String message;

    SuccessCode(HttpStatus httpStatus, String code, String message) {
        this.httpStatus = httpStatus;
        this.code = code;
        this.message = message;
    }
}