package com.aits.hivelink_demo.exception;

import com.aits.hivelink_demo.dto.response.ErrorResponse;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingPathVariableException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.NoHandlerFoundException;

import java.time.LocalDateTime;
import java.util.UUID;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ApiException.class)
    public ResponseEntity<ErrorResponse> handleApiException(
            ApiException ex,
            HttpServletRequest request
    ){
        ErrorResponse response = ErrorResponse.builder()
                .requestId(UUID.randomUUID().toString())
                .timestamp(LocalDateTime.now().toString())
                .status(new ErrorResponse.Status(HttpStatus.NOT_FOUND))
                .message("User not found")
                .path(request.getRequestURI())
                .data(new Object())
                .build();

        return new ResponseEntity<>(response, ex.getStatus());
    }

    // ============================================================
    // 2. METHOD ARGUMENT NOT VALID EXCEPTION
    // ============================================================
    // Handles @Valid / @Validated validation errors.
    // Example: @NotBlank, @NotNull, @Email, @Size, @Min / @Max
    //
    // Usually occurs when validation of @RequestBody fails.
    // ============================================================

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleValidationException(
            MethodArgumentNotValidException ex,
            HttpServletRequest request
    ) {

        ErrorResponse response = ErrorResponse.builder()
                .requestId(UUID.randomUUID().toString())
                .timestamp(LocalDateTime.now().toString())
                .status(new ErrorResponse.Status(HttpStatus.BAD_REQUEST))
                .message("Validation failed")
                .path(request.getRequestURI())
                .data(ex.getBindingResult().getFieldErrors())
                .build();

        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }




    // ============================================================
    // 3. MISSING SERVLET REQUEST PARAMETER EXCEPTION
    // ============================================================
    // Handles a missing required @RequestParam.
    // Example:@RequestParam String id
    //
    // API: GET /users?id=10 // If "id" is not provided, this exception occurs.
    // HTTP STATUS: 400 BAD_REQUEST
    // ============================================================

    @ExceptionHandler(MissingServletRequestParameterException.class)
    public ResponseEntity<ErrorResponse> handleMissingRequestParameter(
            MissingServletRequestParameterException ex,
            HttpServletRequest request
    ) {

        ErrorResponse response = ErrorResponse.builder()
                .requestId(UUID.randomUUID().toString())
                .timestamp(LocalDateTime.now().toString())
                .status(new ErrorResponse.Status(HttpStatus.BAD_REQUEST))
                .message("Required parameter '" + ex.getParameterName() + "' is missing")
                .path(request.getRequestURI())
                .data(null)
                .build();

        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }


    // ============================================================
    // 4. MISSING PATH VARIABLE EXCEPTION
    // ============================================================
    // Handles a missing required @PathVariable.
    // Example: @GetMapping("/users/{id}") || @PathVariable String id
    //
    // Occurs when the required path variable cannot be resolved.
    //
    // HTTP STATUS: 400 BAD_REQUEST
    // ============================================================

    @ExceptionHandler(MissingPathVariableException.class)
    public ResponseEntity<ErrorResponse> handleMissingPathVariable(
            MissingPathVariableException ex,
            HttpServletRequest request
    ) {

        ErrorResponse response = ErrorResponse.builder()
                .requestId(UUID.randomUUID().toString())
                .timestamp(LocalDateTime.now().toString())
                .status(new ErrorResponse.Status(HttpStatus.BAD_REQUEST))
                .message("Required path variable is missing")
                .path(request.getRequestURI())
                .data(null)
                .build();

        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }


    // ============================================================
    // 5. HTTP MESSAGE NOT READABLE EXCEPTION
    // ============================================================
    // Handles invalid or malformed request body data.
    //
    // Example:
    // Client sends invalid JSON.
    //
    // Expected:
    // {
    //     "name": "Vishu",
    //     "age": 25
    // }
    //
    // But malformed JSON is sent.
    //
    // HTTP STATUS: 400 BAD_REQUEST
    // ============================================================

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ErrorResponse> handleMessageNotReadable(
            HttpMessageNotReadableException ex,
            HttpServletRequest request
    ) {

        ErrorResponse response = ErrorResponse.builder()
                .requestId(UUID.randomUUID().toString())
                .timestamp(LocalDateTime.now().toString())
                .status(new ErrorResponse.Status(HttpStatus.BAD_REQUEST))
                .message("Malformed or invalid request body")
                .path(request.getRequestURI())
                .data(null)
                .build();

        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }


    // ============================================================
    // 6. METHOD ARGUMENT TYPE MISMATCH EXCEPTION
    // ============================================================
    // Handles an incorrect parameter data type.
    //
    // Example:
    // @RequestParam Integer id
    //
    // Request:
    // GET /users?id=abc
    //
    // "abc" cannot be converted to Integer.
    //
    // HTTP STATUS: 400 BAD_REQUEST
    // ============================================================

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<ErrorResponse> handleTypeMismatch(
            MethodArgumentTypeMismatchException ex,
            HttpServletRequest request
    ) {

        ErrorResponse response = ErrorResponse.builder()
                .requestId(UUID.randomUUID().toString())
                .timestamp(LocalDateTime.now().toString())
                .status(new ErrorResponse.Status(HttpStatus.BAD_REQUEST))
                .message("Invalid value for parameter '" + ex.getName() + "'")
                .path(request.getRequestURI())
                .data(null)
                .build();

        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }


    // ============================================================
    // 7. HTTP REQUEST METHOD NOT SUPPORTED EXCEPTION
    // ============================================================
    // Handles an unsupported HTTP method.
    //
    // Example:
    // API supports POST /users
    //
    // But client sends:
    // GET /users
    //
    // HTTP STATUS: 405 METHOD_NOT_ALLOWED
    // ============================================================

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ResponseEntity<ErrorResponse> handleMethodNotSupported(
            HttpRequestMethodNotSupportedException ex,
            HttpServletRequest request
    ) {

        ErrorResponse response = ErrorResponse.builder()
                .requestId(UUID.randomUUID().toString())
                .timestamp(LocalDateTime.now().toString())
                .status(new ErrorResponse.Status(HttpStatus.BAD_REQUEST))
                .message("Request method '" + ex.getMethod() + "' is not supported")
                .path(request.getRequestURI())
                .data(null)
                .build();

        return new ResponseEntity<>(response, HttpStatus.METHOD_NOT_ALLOWED);
    }


    // ============================================================
    // 8. HTTP MEDIA TYPE NOT SUPPORTED EXCEPTION
    // ============================================================
    // Handles an unsupported Content-Type.
    //
    // Example:
    // API expects:
    // Content-Type: application/json
    //
    // But client sends:
    // Content-Type: text/plain
    //
    // HTTP STATUS: 415 UNSUPPORTED_MEDIA_TYPE
    // ============================================================

    @ExceptionHandler(HttpMediaTypeNotSupportedException.class)
    public ResponseEntity<ErrorResponse> handleMediaTypeNotSupported(
            HttpMediaTypeNotSupportedException ex,
            HttpServletRequest request
    ) {

        ErrorResponse response = ErrorResponse.builder()
                .requestId(UUID.randomUUID().toString())
                .timestamp(LocalDateTime.now().toString())
                .status(new ErrorResponse.Status(HttpStatus.UNSUPPORTED_MEDIA_TYPE))
                .message("Content-Type is not supported")
                .path(request.getRequestURI())
                .data(null)
                .build();

        return new ResponseEntity<>(response, HttpStatus.UNSUPPORTED_MEDIA_TYPE);
    }


    // ============================================================
    // 9. NO HANDLER FOUND EXCEPTION
    // ============================================================
    // Handles requests where no matching controller endpoint exists.
    //
    // Example:
    // GET /api/unknown
    //
    // If the endpoint does not exist, this exception can be handled.
    //
    // HTTP STATUS: 404 NOT_FOUND
    //
    // NOTE:
    // This depends on your Spring MVC configuration for 404 handling.
    // ============================================================

    @ExceptionHandler(NoHandlerFoundException.class)
    public ResponseEntity<ErrorResponse> handleNoHandlerFound(
            NoHandlerFoundException ex,
            HttpServletRequest request
    ) {

        ErrorResponse response = ErrorResponse.builder()
                .requestId(UUID.randomUUID().toString())
                .timestamp(LocalDateTime.now().toString())
                .status(new ErrorResponse.Status(HttpStatus.NOT_FOUND))
                .message("Endpoint not found")
                .path(request.getRequestURI())
                .data(null)
                .build();

        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }


    // ============================================================
    // 10. GENERIC EXCEPTION
    // ============================================================
    // Global fallback handler for unexpected/unhandled exceptions.
    //
    // Example:
    // NullPointerException
    // RuntimeException
    // Unexpected database errors
    // Any exception not handled by the handlers above.
    //
    // HTTP STATUS: 500 INTERNAL_SERVER_ERROR
    //
    // Keep this as the general/fallback handler.
    // ============================================================

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleGenericException(
            Exception ex,
            HttpServletRequest request
    ) {

        ErrorResponse response = ErrorResponse.builder()
                .requestId(UUID.randomUUID().toString())
                .timestamp(LocalDateTime.now().toString())
                .status(new ErrorResponse.Status(HttpStatus.INTERNAL_SERVER_ERROR))
                .message("Internal server error")
                .path(request.getRequestURI())
                .data(null)
                .build();

        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }

}
