package com.marvim.wishlist.config.handler;

import com.marvim.wishlist.adapter.controller.dto.response.ApiResponse;
import com.marvim.wishlist.config.handler.exception.ProductAlreadyInWishlistException;
import com.marvim.wishlist.config.handler.exception.ProductNotFoundException;
import com.marvim.wishlist.config.handler.exception.WishlistLimitExceededException;
import com.marvim.wishlist.config.handler.exception.WishlistNotFoundException;
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
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ProductAlreadyInWishlistException.class)
    public ResponseEntity<ApiResponse<ErrorResponse>> handleProductAlreadyInWishlistException(ProductAlreadyInWishlistException ex) {
        List<ErrorResponse.ErrorDetail> errors = new ArrayList<>();
        errors.add(new ErrorResponse.ErrorDetail(null, ex.getMessage()));
        ErrorResponse errorResponse = new ErrorResponse("PRODUCT_ALREADY_IN_WISHLIST", errors);
        ApiResponse<ErrorResponse> apiResponse = new ApiResponse<>(errorResponse);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(apiResponse);
    }

    @ExceptionHandler(WishlistLimitExceededException.class)
    public ResponseEntity<ErrorResponse> handleWishlistLimitExceededException(WishlistLimitExceededException ex) {
        List<ErrorResponse.ErrorDetail> errors = new ArrayList<>();
        errors.add(new ErrorResponse.ErrorDetail(null, ex.getMessage()));
        ErrorResponse errorResponse = new ErrorResponse("LIMIT_EXCEEDED", errors);
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(WishlistNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleWishlistNotFoundException(WishlistNotFoundException ex) {
        List<ErrorResponse.ErrorDetail> errors = new ArrayList<>();
        errors.add(new ErrorResponse.ErrorDetail(null, ex.getMessage()));
        ErrorResponse errorResponse = new ErrorResponse("WISHLIST_NOT_FOUND_ERROR", errors);
        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(ProductNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleProductNotFoundException(ProductNotFoundException ex) {
        List<ErrorResponse.ErrorDetail> errors = new ArrayList<>();
        errors.add(new ErrorResponse.ErrorDetail(null, ex.getMessage()));
        ErrorResponse errorResponse = new ErrorResponse("PRODUCT_NOT_FOUND_ERROR", errors);
        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }

}
