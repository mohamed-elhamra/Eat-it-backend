package com.melhamra.eatItBackend.services;

import com.melhamra.eatItBackend.dtos.CategoryDto;
import com.melhamra.eatItBackend.dtos.ProductDto;

import java.util.List;

public interface CategoryService {

    CategoryDto createCategory(CategoryDto categoryDto);

    List<ProductDto> getProductsByCategory(String categoryPublicId);

}
