package com.melhamra.eatItBackend.services;

import com.melhamra.eatItBackend.dtos.ProductDto;
import org.springframework.web.multipart.MultipartFile;

public interface ProductService {

    ProductDto createProduct(ProductDto productDto, MultipartFile multipartFile) ;

}
