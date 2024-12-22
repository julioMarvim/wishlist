package com.marvim.wishlist.application.mapper;

import com.marvim.wishlist.adapter.controller.dto.request.AddProductRequest;
import com.marvim.wishlist.adapter.controller.dto.response.ProductResponse;
import com.marvim.wishlist.adapter.controller.dto.response.WishlistResponse;
import com.marvim.wishlist.domain.entity.Product;
import com.marvim.wishlist.domain.entity.Wishlist;

import java.util.List;
import java.util.stream.Collectors;

public class WishlistMapper {

    public static WishlistResponse toResponse(Wishlist request) {
        List<ProductResponse> productResponses = mapProductsToProductResponses(request.getProducts());
        return WishlistResponse.builder()
                .clientId(request.getClientId())
                .products(productResponses)
                .build();
    }

    private static List<ProductResponse> mapProductsToProductResponses(List<Product> products) {
        return products.stream()
                .map(product -> new ProductResponse(
                        product.getId(),
                        product.getName(),
                        product.getDescription(),
                        product.getPrice()))
                .collect(Collectors.toList());
    }
}
