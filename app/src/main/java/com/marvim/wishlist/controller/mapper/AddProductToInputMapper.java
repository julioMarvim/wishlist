package com.marvim.wishlist.controller.mapper;

import com.marvim.wishlist.controller.dto.request.AddProductRequest;
import com.marvim.wishlist.input.dto.request.AddProductRequestInput;

public class AddProductToInputMapper {

    public static AddProductRequestInput toInput(AddProductRequest request) {
        return AddProductRequestInput.builder()
                .id(request.getId())
                .name(request.getName())
                .description(request.getDescription())
                .build();
    }
}
