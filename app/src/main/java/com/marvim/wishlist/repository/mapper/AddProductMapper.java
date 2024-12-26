package com.marvim.wishlist.repository.mapper;

import com.marvim.wishlist.controller.dto.response.ProductResponse;
import com.marvim.wishlist.repository.entity.ProductEntity;
import com.marvim.wishlist.output.dto.request.AddProductRequestOutputDto;

public class AddProductMapper {

    public static ProductEntity toEntity(AddProductRequestOutputDto addProductRequestOutputDto) {
        return ProductEntity.builder()
                .id(addProductRequestOutputDto.getId())
                .name(addProductRequestOutputDto.getName())
                .description(addProductRequestOutputDto.getDescription())
                .build();
    }

    public static ProductResponse toResponse(ProductEntity request) {
        return ProductResponse.builder()
                .id(request.getId())
                .name(request.getName())
                .description(request.getDescription())
                .build();
    }
}
