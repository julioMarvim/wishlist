package com.marvim.wishlist.usecases.mapper;

import com.marvim.wishlist.input.dto.response.ProductResponseInputDto;
import com.marvim.wishlist.input.dto.response.WishlistResponseInputDto;
import com.marvim.wishlist.output.dto.response.ProductResponseOutputDto;
import com.marvim.wishlist.output.dto.response.WishlistResponseOutputDto;

import java.util.List;
import java.util.stream.Collectors;

public class WishlistResponseDtoMapper {

    public static WishlistResponseInputDto toInputDto(WishlistResponseOutputDto wishlistResponseOutputDto) {
        List<ProductResponseInputDto> productResponses = toProductsInputDto(wishlistResponseOutputDto.getProducts());
        return WishlistResponseInputDto.builder()
                .clientId(wishlistResponseOutputDto.getClientId())
                .products(productResponses)
                .build();
    }

    private static List<ProductResponseInputDto> toProductsInputDto(List<ProductResponseOutputDto> productResponseOutputDtos) {
        return productResponseOutputDtos.stream()
                .map(product -> new ProductResponseInputDto(
                        product.getId(),
                        product.getName(),
                        product.getDescription()))
                .collect(Collectors.toList());
    }
}
