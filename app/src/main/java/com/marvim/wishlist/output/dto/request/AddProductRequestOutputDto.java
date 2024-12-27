package com.marvim.wishlist.output.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class AddProductRequestOutputDto {
    private String id;
    private String name;
    private String description;
}
