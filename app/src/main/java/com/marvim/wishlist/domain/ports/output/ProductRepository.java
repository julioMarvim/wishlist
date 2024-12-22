package com.marvim.wishlist.domain.ports.output;

import com.marvim.wishlist.domain.entity.Product;

public interface ProductRepository {
    void save(Product product);
}