package com.marvim.wishlist.controller.mapper;

import com.marvim.wishlist.controller.dto.response.ProductResponseDto;
import com.marvim.wishlist.controller.dto.response.WishlistResponseDto;
import com.marvim.wishlist.input.dto.response.ProductResponseInputDto;
import com.marvim.wishlist.input.dto.response.WishlistResponseInputDto;

import java.util.List;
import java.util.stream.Collectors;

public class WishlistToResponseMapper {

    public static WishlistResponseDto toResponse(WishlistResponseInputDto wishlistResponseInputDto) {
        List<ProductResponseDto> productResponseDtos = mapProductsToProductResponses(wishlistResponseInputDto.getProducts());
        return new WishlistResponseDto(
                wishlistResponseInputDto.getId(),
                wishlistResponseInputDto.getClientId(),
                productResponseDtos
        );
    }

    private static List<ProductResponseDto> mapProductsToProductResponses(List<ProductResponseInputDto> productResponseInputDtos) {
        return productResponseInputDtos.stream()
                .map(product -> new ProductResponseDto(
                        product.getId(),
                        product.getName(),
                        product.getDescription()))
                .collect(Collectors.toList());
    }
}
