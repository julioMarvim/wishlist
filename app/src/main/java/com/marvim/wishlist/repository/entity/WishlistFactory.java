package com.marvim.wishlist.repository.entity;

import java.util.ArrayList;

public class WishlistFactory {
    public static WishlistEntity createNew(String clientId) {
        return WishlistEntity.builder()
                .clientId(clientId)
                .products(new ArrayList<>())
                .build();
    }
}
