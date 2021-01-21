package com.melhamra.eatItBackend.controllers;

import com.melhamra.eatItBackend.dtos.CategoryDto;
import com.melhamra.eatItBackend.requests.CategoryRequest;
import com.melhamra.eatItBackend.responses.CategoryResponse;
import com.melhamra.eatItBackend.responses.ProductResponse;
import com.melhamra.eatItBackend.services.CategoryService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/categories")
public class CategoryController {

    @Autowired
    CategoryService categoryService;
    @Autowired
    ModelMapper modelMapper;

    @PostMapping
    public ResponseEntity<CategoryResponse> createResponse(@RequestParam("name") String name, @RequestParam("image") MultipartFile image){
        CategoryDto category = categoryService.createCategory(name, image);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(modelMapper.map(category, CategoryResponse.class));
    }

    @GetMapping("/{categoryPublicId}/products")
    public ResponseEntity<List<ProductResponse>> getProductsByCategory(@PathVariable String categoryPublicId){
        List<ProductResponse> products = categoryService.getProductsByCategory(categoryPublicId).stream()
                .map(productDto -> modelMapper.map(productDto, ProductResponse.class))
                .collect(Collectors.toList());

        return ResponseEntity.ok(products);
    }

}
