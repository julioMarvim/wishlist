package com.marvim.wishlist.usecases.mapper;

import com.marvim.wishlist.input.dto.response.ProductResponseInput;
import com.marvim.wishlist.input.dto.response.WishlistResponseInput;
import com.marvim.wishlist.output.dto.response.ProductResponseOutput;
import com.marvim.wishlist.output.dto.response.WishlistResponseOutput;

import java.util.List;
import java.util.stream.Collectors;

public class WishlistToInputMapper {

    public static WishlistResponseInput toInputDto(WishlistResponseOutput wishlistResponseOutput) {
        List<ProductResponseInput> productResponses = toProductsInputDto(wishlistResponseOutput.products());
        return new WishlistResponseInput(wishlistResponseOutput.id(), wishlistResponseOutput.clientId(), productResponses);
    }

    private static List<ProductResponseInput> toProductsInputDto(List<ProductResponseOutput> productResponseOutput) {
        return productResponseOutput.stream()
                .map(product -> new ProductResponseInput(
                        product.id(),
                        product.name(),
                        product.description()))
                .collect(Collectors.toList());
    }
}
