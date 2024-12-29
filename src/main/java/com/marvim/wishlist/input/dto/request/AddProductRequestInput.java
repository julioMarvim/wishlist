package com.marvim.wishlist.input.dto.request;

import jakarta.validation.constraints.NotBlank;

public record AddProductRequestInput(
        @NotBlank String id,
        @NotBlank String name,
        String description
) {
}
