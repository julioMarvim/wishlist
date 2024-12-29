package com.marvim.wishlist.usecases.mapper;

import com.marvim.wishlist.input.dto.request.AddProductRequestInput;
import com.marvim.wishlist.input.dto.response.ProductResponseInput;
import com.marvim.wishlist.input.dto.response.WishlistResponseInput;
import com.marvim.wishlist.output.dto.request.AddProductRequestOutput;
import com.marvim.wishlist.output.dto.response.ProductResponseOutput;
import com.marvim.wishlist.output.dto.response.WishlistResponseOutput;

import java.util.List;
import java.util.stream.Collectors;

public class UseCaseMappers {
    public static AddProductRequestOutput toOutput(AddProductRequestInput request) {
        return new AddProductRequestOutput(request.id(), request.name(), request.description());
    }

    public static WishlistResponseInput toInput(WishlistResponseOutput wishlistResponseOutput) {
        List<ProductResponseInput> productResponses = toProducts(wishlistResponseOutput.products());
        return new WishlistResponseInput(wishlistResponseOutput.id(), wishlistResponseOutput.clientId(), productResponses);
    }

    private static List<ProductResponseInput> toProducts(List<ProductResponseOutput> productResponseOutput) {
        return productResponseOutput.stream()
                .map(product -> new ProductResponseInput(
                        product.id(),
                        product.name(),
                        product.description()))
                .collect(Collectors.toList());
    }
}
