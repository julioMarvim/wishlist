package com.marvim.wishlist.repository.mapper;

import com.marvim.wishlist.output.dto.response.ProductResponseOutput;
import com.marvim.wishlist.output.dto.response.WishlistResponseOutput;
import com.marvim.wishlist.repository.entity.ProductEntity;
import com.marvim.wishlist.repository.entity.WishlistEntity;

import java.util.List;
import java.util.stream.Collectors;

public class WishlistOutputToEntityMapper {

    public static WishlistEntity toOutputDto(WishlistResponseOutput wishlistDto) {
        List<ProductEntity> productResponses = toProductsEntity(wishlistDto.getProducts());
        return WishlistEntity.builder()
                .id(wishlistDto.getId())
                .clientId(wishlistDto.getClientId())
                .products(productResponses)
                .build();
    }

    private static List<ProductEntity> toProductsEntity(List<ProductResponseOutput> productsDto) {
        return productsDto.stream()
                .map(product -> ProductEntity.builder()
                        .id(product.getId())
                        .name(product.getName())
                        .description(product.getDescription())
                        .build())
                .collect(Collectors.toList());
    }
}
