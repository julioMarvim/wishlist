package com.marvim.wishlist.controller.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AddProductRequest {
    @NotBlank
    private String id;

    @NotBlank
    private String name;

    private String description;
}
