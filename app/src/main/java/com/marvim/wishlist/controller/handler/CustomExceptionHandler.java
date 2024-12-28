package com.marvim.wishlist.controller.handler;

import com.marvim.wishlist.controller.dto.response.ApiResponse;
import com.marvim.wishlist.controller.dto.response.ErrorResponse;
import com.marvim.wishlist.input.exception.ProductAlreadyInWishlistException;
import com.marvim.wishlist.input.exception.ProductNotFoundException;
import com.marvim.wishlist.input.exception.WishlistLimitExceededException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.ArrayList;
import java.util.List;

@ControllerAdvice
public class CustomExceptionHandler extends ResponseEntityExceptionHandler {

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(
            MethodArgumentNotValidException ex, HttpHeaders headers,
            HttpStatusCode status, WebRequest request) {
        List<ErrorResponse.ErrorDetail> errors = new ArrayList<>();
        for (FieldError fieldError : ex.getBindingResult().getFieldErrors()) {
            errors.add(new ErrorResponse.ErrorDetail(fieldError.getField(), fieldError.getDefaultMessage()));
        }

        ErrorResponse errorResponse = new ErrorResponse("VALIDATION_FAILED", errors);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ApiResponse<>(errorResponse));
    }

    @ExceptionHandler(ProductAlreadyInWishlistException.class)
    public ResponseEntity<ApiResponse<ErrorResponse>> handleProductAlreadyInWishlistException(ProductAlreadyInWishlistException ex) {
        List<ErrorResponse.ErrorDetail> errors = new ArrayList<>();
        errors.add(new ErrorResponse.ErrorDetail(null, ex.getMessage()));
        ErrorResponse errorResponse = new ErrorResponse("PRODUCT_ALREADY_IN_WISHLIST", errors);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ApiResponse<>(errorResponse));
    }

    @ExceptionHandler(WishlistLimitExceededException.class)
    public ResponseEntity<ApiResponse<ErrorResponse>> handleWishlistLimitExceededException(WishlistLimitExceededException ex) {
        List<ErrorResponse.ErrorDetail> errors = new ArrayList<>();
        errors.add(new ErrorResponse.ErrorDetail(null, ex.getMessage()));
        ErrorResponse errorResponse = new ErrorResponse("WISHLIST_LIMIT_EXCEEDED", errors);
        return ResponseEntity.status(HttpStatus.CONFLICT).body(new ApiResponse<>(errorResponse));
    }

    @ExceptionHandler(ProductNotFoundException.class)
    public ResponseEntity<ApiResponse<ErrorResponse>> handleProductNotFoundException(ProductNotFoundException ex) {
        List<ErrorResponse.ErrorDetail> errors = new ArrayList<>();
        errors.add(new ErrorResponse.ErrorDetail(null, ex.getMessage()));
        ErrorResponse errorResponse = new ErrorResponse("PRODUCT_NOT_FOUND_ERROR", errors);
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse<>(errorResponse));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse<?>> handleException(Exception ex) {
        List<ErrorResponse.ErrorDetail> errors = List.of(
                new ErrorResponse.ErrorDetail(null, "An unexpected error occurred: " + ex.getMessage())
        );
        ErrorResponse errorResponse = new ErrorResponse("INTERNAL_SERVER_ERROR", errors);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponse<>(errorResponse));
    }
}
