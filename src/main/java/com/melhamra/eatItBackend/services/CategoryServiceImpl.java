package com.melhamra.eatItBackend.services;

import com.melhamra.eatItBackend.dtos.CategoryDto;
import com.melhamra.eatItBackend.dtos.ProductDto;
import com.melhamra.eatItBackend.entities.CategoryEntity;
import com.melhamra.eatItBackend.exceptions.EatItException;
import com.melhamra.eatItBackend.repositories.CategoryRepository;
import com.melhamra.eatItBackend.repositories.ProductRepository;
import com.melhamra.eatItBackend.utils.IDGenerator;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CategoryServiceImpl implements CategoryService{

    @Autowired
    ProductRepository productRepository;
    @Autowired
    CategoryRepository categoryRepository;
    @Autowired
    ModelMapper modelMapper;
    @Autowired
    IDGenerator idGenerator;


    @Override
    public CategoryDto createCategory(CategoryDto categoryDto) {
        CategoryEntity categoryEntity = modelMapper.map(categoryDto, CategoryEntity.class);
        categoryEntity.setPublicId(idGenerator.generateStringId(15));

        return modelMapper.map(categoryRepository.save(categoryEntity), CategoryDto.class);
    }

    @Override
    public List<ProductDto> getProductsByCategory(String categoryPublicId) {
        CategoryEntity category = categoryRepository.findByPublicId(categoryPublicId)
                .orElseThrow(() -> new EatItException("No category found with this id: " + categoryPublicId));

        return productRepository.findByCategory(category).stream()
                .map(productEntity -> modelMapper.map(productEntity, ProductDto.class))
                .collect(Collectors.toList());
    }
}
