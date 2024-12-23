package com.marvim.wishlist.application.mapper;

import com.marvim.wishlist.adapter.controller.dto.request.AddProductRequest;
import com.marvim.wishlist.adapter.controller.dto.response.ProductResponse;
import com.marvim.wishlist.domain.entity.Product;

public class ProductMapper {

    public static Product toDomain(AddProductRequest request) {
        return Product.builder()
                .id(request.getId())
                .name(request.getName())
                .description(request.getDescription())
                .build();
    }

    public static ProductResponse toResponse(Product request) {
        return ProductResponse.builder()
                .id(request.getId())
                .name(request.getName())
                .description(request.getDescription())
                .build();
    }
}
