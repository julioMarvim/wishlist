package com.marvim.wishlist.input.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;

@Getter
@Builder
public class AddProductRequestInputDto {
    @NotBlank
    private String id;

    @NotBlank
    private String name;

    private String description;
}
