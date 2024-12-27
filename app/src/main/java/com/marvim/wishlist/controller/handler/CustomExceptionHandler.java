package com.marvim.wishlist.controller.handler;

import com.marvim.wishlist.controller.dto.response.ApiResponseDto;
import com.marvim.wishlist.controller.dto.response.ErrorResponseDto;
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
        List<ErrorResponseDto.ErrorDetail> errors = new ArrayList<>();
        for (FieldError fieldError : ex.getBindingResult().getFieldErrors()) {
            errors.add(new ErrorResponseDto.ErrorDetail(fieldError.getField(), fieldError.getDefaultMessage()));
        }

        ErrorResponseDto errorResponseDto = new ErrorResponseDto("VALIDATION_FAILED", errors);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ApiResponseDto<>(errorResponseDto));
    }

    @ExceptionHandler(ProductAlreadyInWishlistException.class)
    public ResponseEntity<ApiResponseDto<ErrorResponseDto>> handleProductAlreadyInWishlistException(ProductAlreadyInWishlistException ex) {
        List<ErrorResponseDto.ErrorDetail> errors = new ArrayList<>();
        errors.add(new ErrorResponseDto.ErrorDetail(null, ex.getMessage()));
        ErrorResponseDto errorResponseDto = new ErrorResponseDto("PRODUCT_ALREADY_IN_WISHLIST", errors);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ApiResponseDto<>(errorResponseDto));
    }

    @ExceptionHandler(WishlistLimitExceededException.class)
    public ResponseEntity<ApiResponseDto<ErrorResponseDto>> handleWishlistLimitExceededException(WishlistLimitExceededException ex) {
        List<ErrorResponseDto.ErrorDetail> errors = new ArrayList<>();
        errors.add(new ErrorResponseDto.ErrorDetail(null, ex.getMessage()));
        ErrorResponseDto errorResponseDto = new ErrorResponseDto("WISHLIST_LIMIT_EXCEEDED", errors);
        return ResponseEntity.status(HttpStatus.CONFLICT).body(new ApiResponseDto<>(errorResponseDto));
    }

    @ExceptionHandler(ProductNotFoundException.class)
    public ResponseEntity<ApiResponseDto<ErrorResponseDto>> handleProductNotFoundException(ProductNotFoundException ex) {
        List<ErrorResponseDto.ErrorDetail> errors = new ArrayList<>();
        errors.add(new ErrorResponseDto.ErrorDetail(null, ex.getMessage()));
        ErrorResponseDto errorResponseDto = new ErrorResponseDto("PRODUCT_NOT_FOUND_ERROR", errors);
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponseDto<>(errorResponseDto));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponseDto<?>> handleException(Exception ex) {
        List<ErrorResponseDto.ErrorDetail> errors = List.of(
                new ErrorResponseDto.ErrorDetail(null, "An unexpected error occurred: " + ex.getMessage())
        );
        ErrorResponseDto errorResponseDto = new ErrorResponseDto("INTERNAL_SERVER_ERROR", errors);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponseDto<>(errorResponseDto));
    }
}
