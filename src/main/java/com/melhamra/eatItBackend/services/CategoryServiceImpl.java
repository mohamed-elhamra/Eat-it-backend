package com.melhamra.eatItBackend.services;

import com.melhamra.eatItBackend.dtos.CategoryDto;
import com.melhamra.eatItBackend.entities.CategoryEntity;
import com.melhamra.eatItBackend.repositories.CategoryRepository;
import com.melhamra.eatItBackend.utils.IDGenerator;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CategoryServiceImpl implements CategoryService{

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
}
