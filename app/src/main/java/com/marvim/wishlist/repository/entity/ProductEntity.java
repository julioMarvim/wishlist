package com.marvim.wishlist.repository.entity;

import lombok.*;

@Getter
@Builder
@EqualsAndHashCode
public class ProductEntity {
    private String id;
    private String name;
    private String description;
}
