package com.marvim.wishlist.repository.entity;

import java.util.ArrayList;

public class WishlistFactory {
    public static WishlistEntity createNew(String clientId) {
        System.out.println("---------------------PASOU AQUI -----------------------");
        WishlistEntity wishlist = WishlistEntity.builder()
                .clientId(clientId)
                .products(new ArrayList<>())
                .build();

        System.out.println("[WISHLIST]" +  wishlist);
        return wishlist;
    }
}
