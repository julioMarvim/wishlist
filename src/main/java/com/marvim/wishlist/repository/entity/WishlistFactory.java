package com.marvim.wishlist.repository.entity;

import java.util.ArrayList;

public class WishlistFactory {
    public static WishlistEntity createNew(String clientId) {
        return new WishlistEntity(null, clientId, new ArrayList<>());
    }
}

