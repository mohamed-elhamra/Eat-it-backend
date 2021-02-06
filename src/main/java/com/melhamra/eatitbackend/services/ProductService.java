package com.melhamra.eatitbackend.services;

import com.melhamra.eatitbackend.dtos.ProductDto;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface ProductService {

    ProductDto createProduct(ProductDto productDto, MultipartFile multipartFile);

    ProductDto updateProduct(String productPublicId, ProductDto productDto, MultipartFile multipartFile);

    ProductDto getProductByPublicId(String productPublicId);

    List<ProductDto> getAllProducts();

    void deleteProduct(String productPublicId);

}
