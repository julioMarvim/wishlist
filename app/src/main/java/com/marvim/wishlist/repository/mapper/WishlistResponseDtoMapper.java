package com.marvim.wishlist.repository.mapper;

import com.marvim.wishlist.input.dto.response.ProductResponseInputDto;
import com.marvim.wishlist.input.dto.response.WishlistResponseInputDto;
import com.marvim.wishlist.output.dto.response.ProductResponseOutputDto;
import com.marvim.wishlist.output.dto.response.WishlistResponseOutputDto;
import com.marvim.wishlist.repository.entity.ProductEntity;
import com.marvim.wishlist.repository.entity.WishlistEntity;

import java.util.List;
import java.util.stream.Collectors;

public class WishlistResponseDtoMapper {

    public static WishlistResponseOutputDto toOutputDto(WishlistEntity wishlistEntity) {
        List<ProductResponseOutputDto> productResponses = toProductsOutputDto(wishlistEntity.getProducts());
        return WishlistResponseOutputDto.builder()
                .id(wishlistEntity.getId())
                .clientId(wishlistEntity.getClientId())
                .products(productResponses)
                .build();
    }

    private static List<ProductResponseOutputDto> toProductsOutputDto(List<ProductEntity> productEntities) {
        return productEntities.stream()
                .map(product -> new ProductResponseOutputDto(
                        product.getId(),
                        product.getName(),
                        product.getDescription()))
                .collect(Collectors.toList());
    }
}
