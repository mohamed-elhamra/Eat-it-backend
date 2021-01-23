package com.melhamra.eatItBackend.services;

import com.melhamra.eatItBackend.dtos.CategoryDto;
import com.melhamra.eatItBackend.dtos.ProductDto;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface CategoryService {

    CategoryDto createCategory(String name, MultipartFile image);

    List<CategoryDto> getAllCategories();

    List<ProductDto> getProductsByCategory(String categoryPublicId);

    void deleteCategory(String publicId);

}
