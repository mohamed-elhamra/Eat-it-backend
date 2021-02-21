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

    @MockBean
    ImageRepository imageRepository;

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
    void createProductTest_WhenCategoryNotFound(){
        Exception exception = Assertions.assertThrows(EatItException.class, () -> productService.createProduct(productDto, image));
        String expectedMessage = "Category not found with this id: " + categoryEntity.getPublicId();
        String actualMessage = exception.getMessage();
        Assertions.assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    void updateProductTest(){
        Mockito.when(productRepository.findByPublicId(productDto.getPublicId()))
                .thenReturn(Optional.of(productEntity));
        Mockito.when(categoryRepository.findByPublicId(productDto.getCategoryPublicId()))
                .thenReturn(Optional.of(categoryEntity));
        Mockito.doNothing().when(imageService).delete(productEntity.getImage().getPublicId());
        Mockito.when(imageService.save(Mockito.any(MultipartFile.class)))
                .thenReturn(imageDto);
        Mockito.doNothing().when(imageRepository).deleteByPublicId(productEntity.getImage().getPublicId());
        Mockito.when(productRepository.save(Mockito.any(ProductEntity.class)))
                .thenReturn(productEntity);
        Assertions.assertEquals(productDto, productService.updateProduct(productDto.getPublicId(), productDto, image));
    }

    @Test
    void updateProductTest_WhenProductNotFound(){
        Exception exception = Assertions
                .assertThrows(EatItException.class, () -> productService.updateProduct("azdaefaefae", productDto, image));
        String expectedMessage = "No product found with this id: " + productDto.getPublicId();
        String actualMessage = exception.getMessage();
        Assertions.assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    void updateProductTest_WhenCategoryNotFound(){
        Mockito.when(productRepository.findByPublicId(productDto.getPublicId()))
                .thenReturn(Optional.of(productEntity));
        Exception exception = Assertions
                .assertThrows(EatItException.class, () -> productService.updateProduct("azdaefaefae", productDto, image));
        String expectedMessage = "Category not found with this id: " + categoryEntity.getPublicId();
        String actualMessage = exception.getMessage();
        Assertions.assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    void getProductByPublicIdTest(){
        Mockito.when(productRepository.findByPublicId(productDto.getPublicId()))
                .thenReturn(Optional.of(productEntity));
        Assertions.assertEquals(productDto, productService.getProductByPublicId(productDto.getPublicId()));
    }

    @Test
    void getProductByPublicIdTest_WhenUserNotFound(){
        Exception exception = Assertions
                .assertThrows(EatItException.class, () -> productService.getProductByPublicId("azdaefaefae"));
        String expectedMessage = "No product found with this id: " + "azdaefaefae";
        String actualMessage = exception.getMessage();
        Assertions.assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    void getAllProductsTest(){
        Mockito.when(productRepository.findAll()).thenReturn(Collections.singletonList(productEntity));
        Assertions.assertEquals(Collections.singletonList(productDto), productService.getAllProducts());
    }

    @Test
    void deleteProductTest(){
        Mockito.when(productRepository.findByPublicId(productDto.getPublicId()))
                .thenReturn(Optional.of(productEntity));
        Mockito.doNothing().when(imageService).delete(productEntity.getImage().getPublicId());
        productRepository.delete(productEntity);
        Mockito.verify(productRepository, Mockito.times(1)).delete(productEntity);
    }

    @Test
    void deleteProductTest_WhenProductNotFound(){
        Exception exception = Assertions
                .assertThrows(EatItException.class, () -> productService.deleteProduct("azdaefaefae"));
        String expectedMessage = "No product found with this id: " + "azdaefaefae";
        String actualMessage = exception.getMessage();
        Assertions.assertTrue(actualMessage.contains(expectedMessage));
    }
}