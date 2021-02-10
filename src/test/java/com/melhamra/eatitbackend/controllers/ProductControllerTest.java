package com.melhamra.eatitbackend.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.melhamra.eatitbackend.dtos.ProductDto;
import com.melhamra.eatitbackend.responses.ProductResponse;
import com.melhamra.eatitbackend.services.ProductService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.multipart.MultipartFile;


@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
class ProductControllerTest {

    private static ProductDto productDto;
    private static MockMultipartFile image;

    @Autowired
    private MockMvc mvc;

    @MockBean
    private ProductService productService;

    ObjectMapper om = new ObjectMapper().registerModule(new JavaTimeModule());

    @BeforeAll
    public static void init() {
        productDto = new ProductDto(1L, "azdazd", "Margarita Pizza",
                "Delicious Pizza just try it!", 3, null, "azdazdcazf");
        image =
                new MockMultipartFile("image", "", "application/json", "{\"image\": \"Some data\"}".getBytes());
    }

    /*@Test
    void createProductTest() throws Exception {
        Mockito.when(productService.createProduct(Mockito.any(ProductDto.class), Mockito.any(MultipartFile.class)))
                .thenReturn(productDto);

        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("name","Margarita Pizza");
        params.add("description", "Delicious Pizza just try it!");
        params.add("price", "3");
        params.add("category", "azdazdcazf");

        RequestBuilder request = MockMvcRequestBuilders.multipart("/products")
                .file(image)
                .param("name","Margarita Pizza")
                .param("description", "Delicious Pizza just try it!")
                .param("price", "3")
                .param("category", "azdazdcazf")
                .contentType("multipart/form-data");

        MvcResult result = mvc.perform(request)
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.model().attribute("name", "Margarita Pizza"))
                .andExpect(MockMvcResultMatchers.model().attribute("description", "Delicious Pizza just try it!"))
                .andExpect(MockMvcResultMatchers.model().attribute("price", 3))
                .andExpect(MockMvcResultMatchers.model().attribute("category", "azdazdcazf"))
                .andReturn();

        ProductResponse productResponse = om.readValue(result.getResponse().getContentAsString(), ProductResponse.class);

        Assertions.assertEquals(productResponse.getPublicId(), productDto.getPublicId());

    }*/
}