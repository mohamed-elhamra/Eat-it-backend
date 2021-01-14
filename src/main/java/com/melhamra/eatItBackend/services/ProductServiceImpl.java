package com.melhamra.eatItBackend.services;

import com.melhamra.eatItBackend.dtos.ImageDto;
import com.melhamra.eatItBackend.dtos.ProductDto;
import com.melhamra.eatItBackend.entities.ProductEntity;
import com.melhamra.eatItBackend.repositories.ProductRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    ProductRepository productRepository;
    @Autowired
    ImageService imageService;
    @Autowired
    ModelMapper modelMapper;

    @Override
    public ProductDto createProduct(ProductDto productDto, MultipartFile multipartFile) {
        ImageDto imageDto = imageService.save(multipartFile);
        productDto.setImage(imageDto);
        ProductEntity createdProduct = productRepository.save(modelMapper.map(productDto, ProductEntity.class));

        return modelMapper.map(createdProduct, ProductDto.class);
    }

}
