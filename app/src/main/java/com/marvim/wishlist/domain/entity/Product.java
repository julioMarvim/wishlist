package com.marvim.wishlist.domain.entity;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Product {
    private String id;
    private String clientId;
    private String name;
    private String description;
    private Double price;
}
