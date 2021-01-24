package com.melhamra.eatItBackend.services;

import com.melhamra.eatItBackend.dtos.ProductDto;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface ProductService {

    ProductDto createProduct(ProductDto productDto, MultipartFile multipartFile);

    List<ProductDto> getAllProducts();

    void deleteProduct(String productPublicId);

}
