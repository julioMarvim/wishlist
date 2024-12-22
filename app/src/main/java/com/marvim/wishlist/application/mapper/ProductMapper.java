package com.marvim.wishlist.application.mapper;

import com.marvim.wishlist.adapter.controller.dto.request.AddProductRequest;
import com.marvim.wishlist.domain.entity.Product;

public class ProductMapper {

    public static Product toDomain(AddProductRequest request) {
        return Product.builder()
                .id(request.getId())
                .clientId(request.getClientId())
                .name(request.getName())
                .description(request.getDescription())
                .price(request.getPrice())
                .build();
    }
}
