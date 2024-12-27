package com.marvim.wishlist.output.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class AddProductRequestOutputDto {
    private String id;
    private String name;
    private String description;
}
