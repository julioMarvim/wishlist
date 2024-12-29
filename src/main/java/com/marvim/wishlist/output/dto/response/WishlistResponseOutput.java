package com.marvim.wishlist.output.dto.response;

import java.util.List;

public record WishlistResponseOutput(
        String id,
        String clientId,
        List<ProductResponseOutput> products
) {
}
