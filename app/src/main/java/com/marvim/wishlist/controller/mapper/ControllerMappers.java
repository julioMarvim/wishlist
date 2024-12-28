package com.marvim.wishlist.controller.mapper;

import com.marvim.wishlist.controller.dto.request.AddProductRequest;
import com.marvim.wishlist.controller.dto.response.ProductResponse;
import com.marvim.wishlist.controller.dto.response.WishlistRespons;
import com.marvim.wishlist.input.dto.request.AddProductRequestInput;
import com.marvim.wishlist.input.dto.response.ProductResponseInput;
import com.marvim.wishlist.input.dto.response.WishlistResponseInput;

import java.util.List;
import java.util.stream.Collectors;

public class ControllerMappers {
    public static AddProductRequestInput toInput(AddProductRequest request) {
        return new AddProductRequestInput(request.id(), request.name(), request.description());
    }

    public static WishlistRespons toResponse(WishlistResponseInput wishlistResponseInput) {
        List<ProductResponse> productResponses = mapProductsToProductResponses(wishlistResponseInput.products());
        return new WishlistRespons(
                wishlistResponseInput.id(),
                wishlistResponseInput.clientId(),
                productResponses
        );
    }

    private static List<ProductResponse> mapProductsToProductResponses(List<ProductResponseInput> productResponseInput) {
        return productResponseInput.stream()
                .map(product -> new ProductResponse(
                        product.id(),
                        product.name(),
                        product.description()))
                .collect(Collectors.toList());
    }
}
