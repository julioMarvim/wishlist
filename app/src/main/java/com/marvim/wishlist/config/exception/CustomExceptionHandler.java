package com.marvim.wishlist.config.exception;

import com.marvim.wishlist.adapter.controller.dto.response.ApiResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.List;

@ControllerAdvice
public class CustomExceptionHandler extends ResponseEntityExceptionHandler {

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(
            MethodArgumentNotValidException ex, HttpHeaders headers,
            HttpStatusCode status, WebRequest request) {

        List<FieldErrorMessage> errors = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(fieldError -> new FieldErrorMessage(
                        fieldError.getField(),
                        fieldError.getDefaultMessage()
                ))
                .toList();

        ApiResponse<Void, List<FieldErrorMessage>> response = new ApiResponse<>(false, null, errors);

        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }


    @ExceptionHandler(ProductAlreadyInWishlistException.class)
    public ResponseEntity<ApiResponse<Void, String>> handleProductAlreadyInWishlistException(ProductAlreadyInWishlistException ex) {
        ApiResponse<Void, String> response = new ApiResponse<>(false, null, ex.getMessage());
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

}
