package com.marvim.wishlist.input.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class ProductResponseInput {
    private String id;
    private String name;
    private String description;
}