package com.marvim.wishlist.output.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProductResponseOutputDto {
    private String id;
    private String name;
    private String description;
}