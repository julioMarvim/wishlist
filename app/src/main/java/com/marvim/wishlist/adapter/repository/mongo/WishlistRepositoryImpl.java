package com.marvim.wishlist.adapter.repository.mongo;

import com.marvim.wishlist.domain.entity.Product;
import com.marvim.wishlist.domain.entity.Wishlist;
import com.marvim.wishlist.domain.ports.output.WishlistRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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
        Wishlist wishlist = repository.findByClientId(clientId)
                .orElseThrow(() -> new IllegalArgumentException("Wishlist not found"));

        List<Product> mutableProducts = new ArrayList<>(wishlist.getProducts());
        mutableProducts.removeIf(product -> product.getId().equals(productId));

        wishlist.setProducts(mutableProducts);
        repository.save(wishlist);
    }

    @Override
    public Wishlist findByClientId(String clientId) {
        Optional<Wishlist> wishlist = repository.findByClientId(clientId);
        return wishlist.orElse(null);
    }
}
