package com.marvim.wishlist.adapter.controller.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ApiResponse<T, E> {
    private final boolean success;
    private final T data;
    private final E error;
}


