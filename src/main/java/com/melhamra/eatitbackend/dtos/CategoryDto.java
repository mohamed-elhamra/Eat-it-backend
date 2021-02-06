package com.melhamra.eatitbackend.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CategoryDto {

    private Long id;
    private String publicId;
    private String name;
    private ImageDto image;
    private List<ProductDto> products;

}

