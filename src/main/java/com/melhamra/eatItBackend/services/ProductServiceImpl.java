package com.melhamra.eatItBackend.services;

import com.melhamra.eatItBackend.dtos.ImageDto;
import com.melhamra.eatItBackend.dtos.ProductDto;
import com.melhamra.eatItBackend.entities.CategoryEntity;
import com.melhamra.eatItBackend.entities.ImageEntity;
import com.melhamra.eatItBackend.entities.ProductEntity;
import com.melhamra.eatItBackend.exceptions.EatItException;
import com.melhamra.eatItBackend.repositories.CategoryRepository;
import com.melhamra.eatItBackend.repositories.ImageRepository;
import com.melhamra.eatItBackend.repositories.ProductRepository;
import com.melhamra.eatItBackend.utils.IDGenerator;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
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
                .orElseThrow(() -> new EatItException("No product found with this id: " + productPublicId));
        CategoryEntity categoryEntity = categoryRepository.findByPublicId(productDto.getCategoryPublicId())
                .orElseThrow(() -> new EatItException("Category not found with this id: " + productDto.getCategoryPublicId()));

        if(multipartFile != null){
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
                .orElseThrow(() -> new EatItException("No product found with this id: " + productPublicId));
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
                .orElseThrow(() -> new EatItException("No product found with this id: " + productPublicId));
        imageService.delete(product.getImage().getPublicId());
        productRepository.delete(product);
    }

}
