package com.melhamra.eatitbackend.services;

import com.melhamra.eatitbackend.dtos.CategoryDto;
import com.melhamra.eatitbackend.dtos.ProductDto;
import com.melhamra.eatitbackend.responses.ProductStatisticsResponse;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface CategoryService {

    CategoryDto createCategory(String name, MultipartFile image);

    List<CategoryDto> getAllCategories();

    List<ProductDto> getProductsByCategory(String categoryPublicId);

    void deleteCategory(String publicId);

    List<ProductStatisticsResponse> productStatistics(String categoryPublicId, String duration);

}
