package com.marvim.wishlist.repository.mapper;

import com.marvim.wishlist.output.dto.request.AddProductRequestOutput;
import com.marvim.wishlist.repository.entity.ProductEntity;

public class AddProductToEntityMapper {

    public static ProductEntity toEntity(AddProductRequestOutput addProductRequestOutput) {
        return ProductEntity.builder()
                .id(addProductRequestOutput.getId())
                .name(addProductRequestOutput.getName())
                .description(addProductRequestOutput.getDescription())
                .build();
    }
}
