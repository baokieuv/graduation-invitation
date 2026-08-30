package com.graduation.letter.exception;

import com.graduation.letter.common.ApiResponse;
import com.graduation.letter.common.ResponseFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.List;
import java.util.Map;

@Slf4j
@ControllerAdvice
@RequiredArgsConstructor
public class GlobalExceptionHandler {

    private final ResponseFactory responseFactory;
    private static final String BASE_PACKAGE = "com.graduation.letter";

    private void logExceptionDetails(String context, Exception ex) {
        Throwable rootCause = ex;
        while (rootCause.getCause() != null && rootCause.getCause() != rootCause) {
            rootCause = rootCause.getCause();
        }

        StackTraceElement errorSource = null;
        for (StackTraceElement element : ex.getStackTrace()) {
            if (element.getClassName().startsWith(BASE_PACKAGE)) {
                errorSource = element;
                break;
            }
        }

        if (errorSource == null && ex.getStackTrace().length > 0) {
            errorSource = ex.getStackTrace()[0];
        }

        String location = errorSource != null
                ? String.format("File: %s | Class: %s | Method: %s | Line: %d",
                errorSource.getFileName(), errorSource.getClassName(), errorSource.getMethodName(), errorSource.getLineNumber())
                : "Unknown location";

        log.error("========== ERROR DETAILS ==========");
        log.error("Context    : {}", context);
        log.error("Message    : {}", ex.getMessage());
        log.error("Root Cause : {}", rootCause.toString());
        log.error("Location   : {}", location);
        log.error("===================================");
    }

    @ExceptionHandler(ApiException.class)
    public ResponseEntity<ApiResponse<Object>> handleApiException(ApiException ex) {
        ErrorCode errorCode = ex.getErrorCode();
        logExceptionDetails(String.format("ApiException occurred: code=%s, message='%s'", errorCode.getCode(), ex.getMessage()), ex);

        return responseFactory.error(errorCode, ex.getMessage(), ex.getData());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiResponse<List<FieldErrorDetail>>> handleValidationExceptions(MethodArgumentNotValidException ex) {
        ErrorCode errorCode = ErrorCode.VALIDATION_ERROR;

        List<FieldErrorDetail> details = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(error -> new FieldErrorDetail(error.getField(), error.getDefaultMessage()))
                .toList();

        logExceptionDetails("Validation failed for input parameters", ex);

        return responseFactory.error(errorCode, errorCode.getMessage(), details);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ApiResponse<Object>> handleHttpMessageNotReadable(HttpMessageNotReadableException ex) {
        ErrorCode errorCode = ErrorCode.MALFORMED_REQUEST_BODY;
        logExceptionDetails("Malformed JSON request body", ex);

        return responseFactory.error(errorCode, errorCode.getMessage(), null);
    }

    @ExceptionHandler(MissingServletRequestParameterException.class)
    public ResponseEntity<ApiResponse<Map<String, String>>> handleMissingServletRequestParameter(MissingServletRequestParameterException ex) {
        ErrorCode errorCode = ErrorCode.MISSING_REQUEST_PARAMETER;
        Map<String, String> details = Map.of("parameterName", ex.getParameterName());

        logExceptionDetails("Missing Required Request Parameter: " + ex.getParameterName(), ex);

        return responseFactory.error(errorCode, errorCode.getMessage(), details);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ApiResponse<Object>> handleIllegalArgumentException(IllegalArgumentException ex) {
        ErrorCode errorCode = ErrorCode.INVALID_FORMAT;
        logExceptionDetails("Illegal Argument Exception", ex);

        return responseFactory.error(errorCode, ex.getMessage(), null);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse<Object>> handleGenericException(Exception ex) {
        ErrorCode errorCode = ErrorCode.INTERNAL_SERVER_ERROR;
        logExceptionDetails("Unexpected Internal Server Error", ex);

        return responseFactory.error(errorCode, errorCode.getMessage(), null);
    }
}