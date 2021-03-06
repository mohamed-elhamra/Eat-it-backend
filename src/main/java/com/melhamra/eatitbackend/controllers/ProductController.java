package com.melhamra.eatitbackend.controllers;

import com.melhamra.eatitbackend.dtos.ProductDto;
import com.melhamra.eatitbackend.responses.ProductResponse;
import com.melhamra.eatitbackend.services.ProductService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.stream.Collectors;

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
                                                         @RequestParam("category") String category,
                                                         @RequestParam("image") MultipartFile image) {
        ProductDto productDto = new ProductDto(null, null, name, description, price, null, category);
        ProductDto createdProduct = productService.createProduct(productDto, image);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(modelMapper.map(createdProduct, ProductResponse.class));
    }

    @GetMapping
    public ResponseEntity<List<ProductResponse>> getAllProducts() {
        List<ProductResponse> products = productService.getAllProducts().stream()
                .map(productDto -> modelMapper.map(productDto, ProductResponse.class))
                .collect(Collectors.toList());
        return ResponseEntity.ok(products);
    }

    @GetMapping("/{productPublicId}")
    public ResponseEntity<ProductResponse> getProductByPublicId(@PathVariable String productPublicId) {
        ProductResponse productResponse =
                modelMapper.map(productService.getProductByPublicId(productPublicId), ProductResponse.class);
        return ResponseEntity.ok(productResponse);
    }

    @PatchMapping("/{productPublicId}")
    public ResponseEntity<ProductResponse> updateProduct(@PathVariable String productPublicId,
                                                         @RequestParam("name") String name,
                                                         @RequestParam("description") String description,
                                                         @RequestParam("price") double price,
                                                         @RequestParam("category") String category,
                                                         @RequestParam(name = "image", required = false) MultipartFile image){
        ProductDto productDto = new ProductDto(null, null, name, description, price, null, category);
        ProductDto updatedProduct = productService.updateProduct(productPublicId, productDto, image);

        return ResponseEntity.status(HttpStatus.ACCEPTED).body(modelMapper.map(updatedProduct, ProductResponse.class));
    }

    @DeleteMapping("{productPublicId}")
    public ResponseEntity<String> deleteProduct(@PathVariable String productPublicId) {
        productService.deleteProduct(productPublicId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
