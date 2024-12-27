package com.marvim.wishlist.repository.entity;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;

@Data
@Builder
@Getter
public class ProductEntity {
    private String id;
    private String name;
    private String description;
}
