package com.melhamra.eatItBackend.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.melhamra.eatItBackend.dtos.UserDto;
import com.melhamra.eatItBackend.requests.UserRequest;
import com.melhamra.eatItBackend.responses.UserResponse;
import com.melhamra.eatItBackend.services.UserService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;


@SpringBootTest
@AutoConfigureMockMvc
class UserControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private UserService userService;

    ObjectMapper om = new ObjectMapper();


    @Test
    void createUser() throws Exception {

        UserRequest userRequest = new UserRequest("mohamed elhamra",
                "mohamed@gmail.com", "0678984573", "mohamed123", "user");

        Mockito.when(userService.createUser(Mockito.any(UserDto.class)))
                .thenReturn( new UserDto(1L, "aabtert", "mohamed elhamra",
                        "mohamed@gmail.com", "067875599", "mohamed", "atrfedr", "user"));

        MvcResult result = mvc.perform(MockMvcRequestBuilders.post("/users").contentType(MediaType.APPLICATION_JSON)
                .content(om.writeValueAsString(userRequest)))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andReturn();
        UserResponse userResponse = om.readValue(result.getResponse().getContentAsString(), UserResponse.class);

        Assertions.assertEquals(userRequest.getEmail(), userResponse.getEmail());
    }
}