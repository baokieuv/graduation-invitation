package com.graduation.letter.common;

import com.graduation.letter.exception.ErrorCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
public class ResponseFactory {

    /**
     * Build a success response with custom data and a specific SuccessCode.
     */
    public <T> ResponseEntity<ApiResponse<T>> success(T data, SuccessCode successCode) {
        ApiResponse<T> response = new ApiResponse<>(
                true,
                successCode.getCode(),
                successCode.getMessage(),
                data
        );
        return new ResponseEntity<>(response, successCode.getHttpStatus());
    }

    /**
     * Build a success response with custom data using the default 200 OK code.
     */
    public <T> ResponseEntity<ApiResponse<T>> success(T data) {
        return success(data, SuccessCode.OK);
    }

    /**
     * Build a success response with no data (e.g., for DELETE operations).
     */
    public ResponseEntity<ApiResponse<Object>> success(SuccessCode successCode) {
        return success(null, successCode);
    }

    /**
     * Build an error response with a specific ErrorCode, custom message, and optional data.
     */
    public <T> ResponseEntity<ApiResponse<T>> error(ErrorCode errorCode, String message, T data) {
        ApiResponse<T> response = new ApiResponse<>(
                false,
                errorCode.getCode(),
                message,
                data
        );
        return new ResponseEntity<>(response, errorCode.getHttpStatus());
    }
}