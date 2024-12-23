package com.marvim.wishlist.config.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.http.HttpStatusCode;

import java.util.Arrays;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ApiErrorMessage {

    private HttpStatusCode status;
    private List<FieldErrorMessage> errors;

    public ApiErrorMessage(HttpStatusCode status, FieldErrorMessage error) {
        this.status = status;
        this.errors = Arrays.asList(error);
    }
}
