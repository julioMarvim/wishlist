package com.marvim.wishlist.controller.mapper;

import com.marvim.wishlist.controller.dto.request.AddProductRequest;
import com.marvim.wishlist.input.dto.request.AddProductRequestInput;

public class AddProductToInputMapper {

    public static AddProductRequestInput toInput(AddProductRequest request) {
        return new AddProductRequestInput(request.id(), request.name(), request.description());
    }
}
