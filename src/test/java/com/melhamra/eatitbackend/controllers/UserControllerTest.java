package com.melhamra.eatitbackend.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.melhamra.eatitbackend.dtos.UserDto;
import com.melhamra.eatitbackend.requests.UserRequest;
import com.melhamra.eatitbackend.responses.OrderProductResponse;
import com.melhamra.eatitbackend.responses.OrderResponse;
import com.melhamra.eatitbackend.responses.UserResponse;
import com.melhamra.eatitbackend.services.UserService;
import com.melhamra.eatitbackend.utils.OrderStatus;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.time.Instant;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;


@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
class UserControllerTest {

    private static UserRequest userRequest;
    private static UserDto userDto;
    private static OrderResponse orderResponse;

    @Autowired
    private MockMvc mvc;

    @MockBean
    private UserService userService;

    ObjectMapper om = new ObjectMapper().registerModule(new JavaTimeModule());

    @BeforeAll
    public static void init() {
        userRequest = new UserRequest("mohamed elhamra",
                "mohamed@gmail.com", "0678984573", "mohamed123", "user");
        userDto = new UserDto(1L, "aabtert", "mohamed elhamra",
                "mohamed@gmail.com", "067875599", "mohamed", "atrfedr", "user");

        OrderProductResponse orderProductResponse1 = new OrderProductResponse(5, 3.34, "azadde", "Margarita Pizza");
        OrderProductResponse orderProductResponse2 = new OrderProductResponse(3, 12.34, "azdazd", "Cheese Pizza");

        orderResponse = new OrderResponse("adzdae", "Hay Berlin Madrid",
                OrderStatus.ON_THE_WAY, Instant.now(), "aabtert", Arrays.asList(orderProductResponse1, orderProductResponse2));

    }

    @Test
    void createUserTest() throws Exception {
        Mockito.when(userService.createUser(Mockito.any(UserDto.class)))
                .thenReturn(userDto);

        RequestBuilder request = MockMvcRequestBuilders.post("/users").contentType(MediaType.APPLICATION_JSON)
                .content(om.writeValueAsString(userRequest));
        MvcResult result = mvc.perform(request)
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andReturn();
        UserResponse userResponse = om.readValue(result.getResponse().getContentAsString(), UserResponse.class);

        Assertions.assertEquals(userRequest.getEmail(), userResponse.getEmail());
    }

    @Test
    void getOrdersByUserTest() throws Exception {
        Mockito.when(userService.getOrdersByUser(Mockito.any(String.class)))
                .thenReturn(Collections.singletonList(orderResponse));

        RequestBuilder request = MockMvcRequestBuilders.get("/users/{publicId}/orders", "aabtert")
                .contentType(MediaType.APPLICATION_JSON);
        MvcResult result = mvc.perform(request)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();

        List<OrderResponse> orderResponses = Arrays.asList(om.readValue(result.getResponse().getContentAsString(), OrderResponse[].class));

        Assertions.assertEquals(1, orderResponses.size());
        Assertions.assertEquals(orderResponse, orderResponses.get(0));
    }

    @Test
    void getUserByPublicIdTest() throws Exception {
        Mockito.when(userService.getUserByPublicId(Mockito.any(String.class)))
                .thenReturn(userDto);

        RequestBuilder request = MockMvcRequestBuilders.get("/users/{publicId}", "aabtert")
                .contentType(MediaType.APPLICATION_JSON);
        MvcResult result = mvc.perform(request)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();

        UserResponse userResponse = om.readValue(result.getResponse().getContentAsString(), UserResponse.class);
        Assertions.assertEquals(userDto.getEmail(), userResponse.getEmail());
    }
}