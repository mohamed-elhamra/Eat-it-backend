package com.melhamra.eatItBackend.controllers;

import com.melhamra.eatItBackend.dtos.ProductDto;
import com.melhamra.eatItBackend.responses.ProductResponse;
import com.melhamra.eatItBackend.services.ProductService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/products")
public class ProductController {

    @Autowired
    private ProductService productService;
    @Autowired
    ModelMapper modelMapper;

    @PostMapping
    public ResponseEntity<ProductResponse> createProduct(@RequestParam("name") String name,
                                                         @RequestParam("description") String description,
                                                         @RequestParam("price") double price,
                                                         @RequestParam("image") MultipartFile image){
        ProductDto productDto = new ProductDto(null, name, description, price, null);
        ProductDto createdProduct = productService.createProduct(productDto, image);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(modelMapper.map(createdProduct, ProductResponse.class));
    }

}
