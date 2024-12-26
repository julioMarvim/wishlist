package com.marvim.wishlist.controller.mapper;

import com.marvim.wishlist.controller.dto.request.AddProductRequest;
import com.marvim.wishlist.controller.dto.response.ProductResponse;
import com.marvim.wishlist.repository.entity.ProductEntity;
import com.marvim.wishlist.input.dto.request.AddProductRequestInputDto;

public class AddProductRequestMapper {

    public static AddProductRequestInputDto toDto(AddProductRequest request) {
        return AddProductRequestInputDto.builder()
                .id(request.getId())
                .name(request.getName())
                .description(request.getDescription())
                .build();
    }
}
