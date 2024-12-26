package com.marvim.wishlist.controller.mapper;

import com.marvim.wishlist.controller.dto.request.AddProductRequest;
import com.marvim.wishlist.input.dto.request.AddProductRequestInputDto;

public class AddProductToInputMapper {

    public static AddProductRequestInputDto toInput(AddProductRequest request) {
        return AddProductRequestInputDto.builder()
                .id(request.getId())
                .name(request.getName())
                .description(request.getDescription())
                .build();
    }
}
