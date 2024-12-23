package com.marvim.wishlist.domain.entity;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Data
@Builder
@Getter
@Document(collection = "wishlists")
public class Wishlist {
    @Id
    private String id;
    private String clientId;
    private List<Product> products;
}
