package com.marvim.wishlist.controller.mapper;

import com.marvim.wishlist.controller.dto.response.ProductResponse;
import com.marvim.wishlist.controller.dto.response.WishlistResponse;
import com.marvim.wishlist.input.dto.response.ProductResponseInputDto;
import com.marvim.wishlist.input.dto.response.WishlistResponseInputDto;

import java.util.List;
import java.util.stream.Collectors;

public class WishlistToResponseMapper {

    public static WishlistResponse toResponse(WishlistResponseInputDto wishlistResponseInputDto) {
        List<ProductResponse> productResponses = mapProductsToProductResponses(wishlistResponseInputDto.getProducts());
        return WishlistResponse.builder()
                .id(wishlistResponseInputDto.getId())
                .clientId(wishlistResponseInputDto.getClientId())
                .products(productResponses)
                .build();
    }

    private static List<ProductResponse> mapProductsToProductResponses(List<ProductResponseInputDto> productResponseInputDtos) {
        return productResponseInputDtos.stream()
                .map(product -> new ProductResponse(
                        product.getId(),
                        product.getName(),
                        product.getDescription()))
                .collect(Collectors.toList());
    }
}
