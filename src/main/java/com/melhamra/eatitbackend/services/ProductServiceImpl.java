package com.melhamra.eatitbackend.services;

import com.melhamra.eatitbackend.dtos.ImageDto;
import com.melhamra.eatitbackend.dtos.ProductDto;
import com.melhamra.eatitbackend.entities.CategoryEntity;
import com.melhamra.eatitbackend.entities.ImageEntity;
import com.melhamra.eatitbackend.entities.ProductEntity;
import com.melhamra.eatitbackend.exceptions.EatItException;
import com.melhamra.eatitbackend.repositories.CategoryRepository;
import com.melhamra.eatitbackend.repositories.ImageRepository;
import com.melhamra.eatitbackend.repositories.ProductRepository;
import com.melhamra.eatitbackend.utils.IDGenerator;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    CategoryRepository categoryRepository;
    @Autowired
    ProductRepository productRepository;
    @Autowired
    ImageService imageService;
    @Autowired
    ImageRepository imageRepository;
    @Autowired
    ModelMapper modelMapper;
    @Autowired
    IDGenerator idGenerator;

    private static final String MESSAGE = "No product found with this id: ";

    @Transactional
    @Override
    public ProductDto createProduct(ProductDto productDto, MultipartFile multipartFile) {
        ImageDto imageDto = imageService.save(multipartFile);
        CategoryEntity categoryEntity = categoryRepository.findByPublicId(productDto.getCategoryPublicId())
                .orElseThrow(() -> new EatItException("Category not found with this id: " + productDto.getCategoryPublicId()));

        ProductEntity savedProduct =
                new ProductEntity(null, idGenerator.generateStringId(15), productDto.getName(), productDto.getDescription(),
                        productDto.getPrice(), modelMapper.map(imageDto, ImageEntity.class), null, categoryEntity);

        savedProduct.setCategory(categoryEntity);
        ProductEntity createdProduct = productRepository.save(savedProduct);

        return modelMapper.map(createdProduct, ProductDto.class);
    }

    @Transactional
    @Override
    public ProductDto updateProduct(String productPublicId, ProductDto productDto, MultipartFile multipartFile) {
        ProductEntity productEntity = productRepository.findByPublicId(productPublicId)
                .orElseThrow(() -> new EatItException(MESSAGE + productPublicId));
        CategoryEntity categoryEntity = categoryRepository.findByPublicId(productDto.getCategoryPublicId())
                .orElseThrow(() -> new EatItException("Category not found with this id: " + productDto.getCategoryPublicId()));

        if (multipartFile != null) {
            imageService.delete(productEntity.getImage().getPublicId());
            ImageDto imageDto = imageService.save(multipartFile);
            imageRepository.deleteByPublicId(productEntity.getImage().getPublicId());
            productEntity.setImage(modelMapper.map(imageDto, ImageEntity.class));
        }

        productEntity.setName(productDto.getName());
        productEntity.setDescription(productDto.getDescription());
        productEntity.setPrice(productDto.getPrice());
        productEntity.setCategory(categoryEntity);
        ProductEntity updatedProduct = productRepository.save(productEntity);

        return modelMapper.map(updatedProduct, ProductDto.class);
    }

    @Override
    public ProductDto getProductByPublicId(String productPublicId) {
        ProductEntity productEntity = productRepository.findByPublicId(productPublicId)
                .orElseThrow(() -> new EatItException(MESSAGE + productPublicId));
        return modelMapper.map(productEntity, ProductDto.class);
    }

    @Override
    public List<ProductDto> getAllProducts() {
        return productRepository.findAll().stream()
                .map(productEntity -> modelMapper.map(productEntity, ProductDto.class))
                .collect(Collectors.toList());
    }

    @Transactional
    @Override
    public void deleteProduct(String productPublicId) {
        ProductEntity product = productRepository.findByPublicId(productPublicId)
                .orElseThrow(() -> new EatItException(MESSAGE + productPublicId));
        imageService.delete(product.getImage().getPublicId());
        productRepository.delete(product);
    }

}
