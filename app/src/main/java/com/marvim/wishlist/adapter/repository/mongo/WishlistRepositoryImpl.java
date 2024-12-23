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
    private static final int MAX_PRODUCTS = 20;

    @Override
    public void add(String clientId, Product product) {;

        Wishlist wishlist = repository.findByClientId(clientId)
                .orElseGet(() -> Wishlist.builder()
                        .clientId(clientId)
                        .products(new ArrayList<>())
                        .build());

        if (wishlist.getProducts().size() >= MAX_PRODUCTS) {
            throw new IllegalStateException("A wishlist já contém o número máximo de produtos.");
        }

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
    public Optional<Wishlist> findByClientId(String clientId) {
        return repository.findByClientId(clientId);
    }

}
