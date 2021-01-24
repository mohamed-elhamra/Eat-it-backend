package com.melhamra.eatItBackend.services;

import com.melhamra.eatItBackend.dtos.CategoryDto;
import com.melhamra.eatItBackend.dtos.ImageDto;
import com.melhamra.eatItBackend.dtos.ProductDto;
import com.melhamra.eatItBackend.entities.CategoryEntity;
import com.melhamra.eatItBackend.entities.ImageEntity;
import com.melhamra.eatItBackend.exceptions.EatItException;
import com.melhamra.eatItBackend.repositories.CategoryRepository;
import com.melhamra.eatItBackend.repositories.ProductRepository;
import com.melhamra.eatItBackend.utils.IDGenerator;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CategoryServiceImpl implements CategoryService{

    @Autowired
    ImageService imageService;
    @Autowired
    ProductRepository productRepository;
    @Autowired
    CategoryRepository categoryRepository;
    @Autowired
    ModelMapper modelMapper;
    @Autowired
    IDGenerator idGenerator;


    @Transactional
    @Override
    public CategoryDto createCategory(String name, MultipartFile image) {
        ImageDto imageDto = imageService.save(image);
        CategoryEntity categoryEntity =
                new CategoryEntity(null, idGenerator.generateStringId(15),
                        name, modelMapper.map(imageDto, ImageEntity.class), null);

        return modelMapper.map(categoryRepository.save(categoryEntity), CategoryDto.class);
    }

    @Override
    public List<CategoryDto> getAllCategories() {
        return categoryRepository.findAll().stream()
                .map(categoryEntity -> modelMapper.map(categoryEntity, CategoryDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public List<ProductDto> getProductsByCategory(String categoryPublicId) {
        CategoryEntity category = categoryRepository.findByPublicId(categoryPublicId)
                .orElseThrow(() -> new EatItException("No category found with this id: " + categoryPublicId));

        return productRepository.findByCategory(category).stream()
                .map(productEntity -> modelMapper.map(productEntity, ProductDto.class))
                .collect(Collectors.toList());
    }

    @Transactional
    @Override
    public void deleteCategory(String publicId) {
        CategoryEntity category = categoryRepository.findByPublicId(publicId)
                .orElseThrow(() -> new EatItException("No category found with this id: " + publicId));
        imageService.delete(category.getImage().getPublicId());
        category.getProducts().forEach(productEntity -> imageService.delete(productEntity.getImage().getPublicId()));
        categoryRepository.delete(category);
    }
}
