package com.marvim.wishlist.domain.entity;

import java.util.ArrayList;

public class WishlistFactory {
    public static Wishlist createNew(String clientId) {
        return Wishlist.builder()
                .clientId(clientId)
                .products(new ArrayList<>())
                .build();
    }
}
