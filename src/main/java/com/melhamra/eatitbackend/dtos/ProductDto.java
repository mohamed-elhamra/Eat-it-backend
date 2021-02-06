package com.melhamra.eatitbackend.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductDto {

    private Long id;
    private String publicId;
    private String name;
    private String description;
    private double price;
    private ImageDto image;
    private String categoryPublicId;

}
