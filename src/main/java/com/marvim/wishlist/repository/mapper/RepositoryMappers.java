package com.marvim.wishlist.repository.mapper;

import com.marvim.wishlist.output.dto.request.AddProductRequestOutput;
import com.marvim.wishlist.output.dto.response.ProductResponseOutput;
import com.marvim.wishlist.output.dto.response.WishlistResponseOutput;
import com.marvim.wishlist.repository.entity.ProductEntity;
import com.marvim.wishlist.repository.entity.WishlistEntity;

import java.util.List;
import java.util.stream.Collectors;

public class RepositoryMappers {
    public static ProductEntity toProduct(AddProductRequestOutput addProductRequestOutput) {
        return new ProductEntity(addProductRequestOutput.id(), addProductRequestOutput.name(), addProductRequestOutput.description());
    }

    public static WishlistEntity toWishlist(WishlistResponseOutput wishlistDto) {
        List<ProductEntity> productResponses = toProducts(wishlistDto.products());
        return new WishlistEntity(wishlistDto.id(), wishlistDto.clientId(), productResponses);
    }

    private static List<ProductEntity> toProducts(List<ProductResponseOutput> productsDto) {
        return productsDto.stream()
                .map(product -> new ProductEntity(product.id(), product.name(), product.description()))
                .collect(Collectors.toList());
    }

    public static WishlistResponseOutput toOutput(WishlistEntity wishlistEntity) {
        List<ProductResponseOutput> productResponses = toProductsOutput(wishlistEntity.products());
        return new WishlistResponseOutput(wishlistEntity.id(), wishlistEntity.clientId(), productResponses);
    }

    private static List<ProductResponseOutput> toProductsOutput(List<ProductEntity> productEntities) {
        return productEntities.stream()
                .map(product -> new ProductResponseOutput(
                        product.id(),
                        product.name(),
                        product.description()))
                .collect(Collectors.toList());
    }
}
