package com.marvim.wishlist.usecases.mapper;

import com.marvim.wishlist.input.dto.request.AddProductRequestInput;
import com.marvim.wishlist.output.dto.request.AddProductRequestOutput;

public class AddProductToOutputMapper {

    public static AddProductRequestOutput toOutputDto(AddProductRequestInput request) {
        return new AddProductRequestOutput(request.id(), request.name(), request.description());
    }
}
