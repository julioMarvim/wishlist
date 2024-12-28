package com.marvim.wishlist.repository.mapper;

import com.marvim.wishlist.output.dto.response.ProductResponseOutput;
import com.marvim.wishlist.output.dto.response.WishlistResponseOutput;
import com.marvim.wishlist.repository.entity.ProductEntity;
import com.marvim.wishlist.repository.entity.WishlistEntity;

import java.util.List;
import java.util.stream.Collectors;

public class WishlistOutputToEntityMapper {

    public static WishlistEntity toOutputDto(WishlistResponseOutput wishlistDto) {
        List<ProductEntity> productResponses = toProductsEntity(wishlistDto.products());
        return new WishlistEntity(wishlistDto.id(), wishlistDto.clientId(), productResponses);
    }

    private static List<ProductEntity> toProductsEntity(List<ProductResponseOutput> productsDto) {
        return productsDto.stream()
                .map(product -> new ProductEntity(product.id(), product.name(), product.description()))
                .collect(Collectors.toList());
    }
}
