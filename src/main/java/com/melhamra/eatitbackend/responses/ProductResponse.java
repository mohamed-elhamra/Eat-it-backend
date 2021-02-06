package com.melhamra.eatitbackend.responses;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductResponse {

    private String publicId;
    private String name;
    private String description;
    private String categoryPublicId;
    private double price;
    private String imageUrl;

}
