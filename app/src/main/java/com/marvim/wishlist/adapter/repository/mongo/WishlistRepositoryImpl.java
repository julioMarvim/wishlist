package com.marvim.wishlist.adapter.repository.mongo;

import com.marvim.wishlist.domain.entity.Product;
import com.marvim.wishlist.domain.entity.Wishlist;
import com.marvim.wishlist.domain.ports.output.WishlistRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;

@Repository
@RequiredArgsConstructor
public class WishlistRepositoryImpl implements WishlistRepository {

    private final SpringDataWishlistRepository repository;

    @Override
    public void add(Product product) {
        String clientId = "client-id";

        Wishlist wishlist = repository.findByClientId(clientId)
                .orElseGet(() -> Wishlist.builder()
                        .clientId(clientId)
                        .products(new ArrayList<>())
                        .build());

        wishlist.getProducts().add(product);
        repository.save(wishlist);
    }

    @Override
    public void remove(String clientId, String productId) {

    }
}
