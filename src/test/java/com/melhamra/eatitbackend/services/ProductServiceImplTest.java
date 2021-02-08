package com.melhamra.eatitbackend.services;

import com.melhamra.eatitbackend.dtos.ImageDto;
import com.melhamra.eatitbackend.dtos.ProductDto;
import com.melhamra.eatitbackend.entities.CategoryEntity;
import com.melhamra.eatitbackend.entities.ImageEntity;
import com.melhamra.eatitbackend.entities.ProductEntity;
import com.melhamra.eatitbackend.exceptions.EatItException;
import com.melhamra.eatitbackend.repositories.CategoryRepository;
import com.melhamra.eatitbackend.repositories.ProductRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.util.Collections;
import java.util.Optional;


@SpringBootTest
class ProductServiceImplTest {

    private static ImageDto imageDto;
    private static ProductDto productDto;
    private static CategoryEntity categoryEntity;
    private static ProductEntity productEntity;
    private static MockMultipartFile image;

    @Autowired
    ProductService productService;

    @Autowired
    ModelMapper modelMapper;

    @MockBean
    ImageService imageService;

    @MockBean
    CategoryRepository categoryRepository;

    @MockBean
    ProductRepository productRepository;

    @BeforeAll
    public static void init() {
        imageDto =
                new ImageDto(1L, "xd6QdsT4Vdmwnum", "xd6QdsT4Vdmwnum.jpeg", "http://142.123.1.87:8080/api/images/xd6QdsT4Vdmwnum");
        ImageEntity imageEntity =
                new ImageEntity(1L, "xd6QdsT4Vdmwnum", "xd6QdsT4Vdmwnum.jpeg", "http://142.123.1.87:8080/api/images/xd6QdsT4Vdmwnum");
        productDto =
                new ProductDto(1L, "azdaefaefae", "Margeritta Pizza", "Delicious Pizza just try it!", 3, imageDto, "aevaevavzvvvr");
        productEntity =
                new ProductEntity(1L, "azdaefaefae", "Margeritta Pizza", "Delicious Pizza just try it!", 3, imageEntity, null, null);
        categoryEntity =
                new CategoryEntity(1L, "aevaevavzvvvr", "Pizza", null, Collections.singletonList(productEntity));
        productEntity.setCategory(categoryEntity);
        image =
                new MockMultipartFile("image", "", "application/json", "{\"image\": \"Some data\"}".getBytes());
    }

    @Test
    void createProductTest() {
        Mockito.when(imageService.save(Mockito.any(MultipartFile.class)))
                .thenReturn(imageDto);
        Mockito.when(categoryRepository.findByPublicId(productDto.getCategoryPublicId()))
                .thenReturn(Optional.of(categoryEntity));
        Mockito.when(productRepository.save(Mockito.any(ProductEntity.class)))
                .thenReturn(productEntity);
        Assertions.assertEquals(productDto, productService.createProduct(productDto, image));
    }

    @Test
    void createProduct_WhenCategoryNotFound(){
        Exception exception = Assertions.assertThrows(EatItException.class, () -> productService.createProduct(productDto, image));
        String expectedMessage = "Category not found with this id: " + categoryEntity.getPublicId();
        String actualMessage = exception.getMessage();
        Assertions.assertTrue(actualMessage.contains(expectedMessage));
    }
}