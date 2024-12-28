package com.marvim.wishlist.usecases.mapper;

import com.marvim.wishlist.input.dto.response.ProductResponseInput;
import com.marvim.wishlist.input.dto.response.WishlistResponseInput;
import com.marvim.wishlist.output.dto.response.ProductResponseOutput;
import com.marvim.wishlist.output.dto.response.WishlistResponseOutput;

import java.util.List;
import java.util.stream.Collectors;

public class WishlistToInputMapper {

    public static WishlistResponseInput toInputDto(WishlistResponseOutput wishlistResponseOutput) {
        List<ProductResponseInput> productResponses = toProductsInputDto(wishlistResponseOutput.getProducts());
        return WishlistResponseInput.builder()
                .id(wishlistResponseOutput.getId())
                .clientId(wishlistResponseOutput.getClientId())
                .products(productResponses)
                .build();
    }

    private static List<ProductResponseInput> toProductsInputDto(List<ProductResponseOutput> productResponseOutput) {
        return productResponseOutput.stream()
                .map(product -> new ProductResponseInput(
                        product.getId(),
                        product.getName(),
                        product.getDescription()))
                .collect(Collectors.toList());
    }
}
