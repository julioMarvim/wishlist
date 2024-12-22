package com.marvim.wishlist.adapter.controller.dto.request;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AddProductRequest {
    private String id;
    private String name;
    private String description;
    private Double price;
}
