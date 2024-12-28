package com.marvim.wishlist.input.dto.response;

import java.util.List;

public record WishlistResponseInput(
        String id,
        String clientId,
        List<ProductResponseInput> products
) {
}
