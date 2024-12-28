package com.marvim.wishlist.repository.mapper;

import com.marvim.wishlist.output.dto.response.ProductResponseOutput;
import com.marvim.wishlist.output.dto.response.WishlistResponseOutput;
import com.marvim.wishlist.repository.entity.ProductEntity;
import com.marvim.wishlist.repository.entity.WishlistEntity;

import java.util.List;
import java.util.stream.Collectors;

public class WishlistToOutputMapper {

    public static WishlistResponseOutput toOutputDto(WishlistEntity wishlistEntity) {
        List<ProductResponseOutput> productResponses = toProductsOutputDto(wishlistEntity.getProducts());
        return WishlistResponseOutput.builder()
                .id(wishlistEntity.getId())
                .clientId(wishlistEntity.getClientId())
                .products(productResponses)
                .build();
    }

    private static List<ProductResponseOutput> toProductsOutputDto(List<ProductEntity> productEntities) {
        return productEntities.stream()
                .map(product -> new ProductResponseOutput(
                        product.getId(),
                        product.getName(),
                        product.getDescription()))
                .collect(Collectors.toList());
    }
}
