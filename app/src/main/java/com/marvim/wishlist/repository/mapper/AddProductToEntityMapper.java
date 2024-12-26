package com.marvim.wishlist.repository.mapper;

import com.marvim.wishlist.output.dto.request.AddProductRequestOutputDto;
import com.marvim.wishlist.repository.entity.ProductEntity;

public class AddProductToEntityMapper {

    public static ProductEntity toEntity(AddProductRequestOutputDto addProductRequestOutputDto) {
        return ProductEntity.builder()
                .id(addProductRequestOutputDto.getId())
                .name(addProductRequestOutputDto.getName())
                .description(addProductRequestOutputDto.getDescription())
                .build();
    }
}
