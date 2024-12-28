package com.marvim.wishlist.input.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class AddProductRequestInput {
    @NotBlank
    private String id;

    @NotBlank
    private String name;

    private String description;
}
