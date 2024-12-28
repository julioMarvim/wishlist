package com.marvim.wishlist.output.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class ProductResponseOutput {
    private String id;
    private String name;
    private String description;
}