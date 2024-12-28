package com.marvim.wishlist.controller.mapper;

import com.marvim.wishlist.controller.dto.response.ProductResponse;
import com.marvim.wishlist.controller.dto.response.WishlistRespons;
import com.marvim.wishlist.input.dto.response.ProductResponseInput;
import com.marvim.wishlist.input.dto.response.WishlistResponseInput;

import java.util.List;
import java.util.stream.Collectors;

public class WishlistToResponseMapper {

    public static WishlistRespons toResponse(WishlistResponseInput wishlistResponseInput) {
        List<ProductResponse> productResponses = mapProductsToProductResponses(wishlistResponseInput.getProducts());
        return new WishlistRespons(
                wishlistResponseInput.getId(),
                wishlistResponseInput.getClientId(),
                productResponses
        );
    }

    private static List<ProductResponse> mapProductsToProductResponses(List<ProductResponseInput> productResponseInput) {
        return productResponseInput.stream()
                .map(product -> new ProductResponse(
                        product.getId(),
                        product.getName(),
                        product.getDescription()))
                .collect(Collectors.toList());
    }
}
