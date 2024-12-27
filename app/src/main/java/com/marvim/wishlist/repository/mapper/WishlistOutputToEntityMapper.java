package com.marvim.wishlist.repository.mapper;

import com.marvim.wishlist.output.dto.response.ProductResponseOutputDto;
import com.marvim.wishlist.output.dto.response.WishlistResponseOutputDto;
import com.marvim.wishlist.repository.entity.ProductEntity;
import com.marvim.wishlist.repository.entity.WishlistEntity;

import java.util.List;
import java.util.stream.Collectors;

public class WishlistOutputToEntityMapper {

    public static WishlistEntity toOutputDto(WishlistResponseOutputDto wishlistDto) {
        List<ProductEntity> productResponses = toProductsEntity(wishlistDto.getProducts());
        return WishlistEntity.builder()
                .id(wishlistDto.getId())
                .clientId(wishlistDto.getClientId())
                .products(productResponses)
                .build();
    }

    private static List<ProductEntity> toProductsEntity(List<ProductResponseOutputDto> productsDto) {
        return productsDto.stream()
                .map(product -> ProductEntity.builder()
                        .id(product.getId())
                        .name(product.getName())
                        .description(product.getDescription())
                        .build())
                .collect(Collectors.toList());
    }
}
