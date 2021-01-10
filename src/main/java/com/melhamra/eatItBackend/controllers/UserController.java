package com.melhamra.eatItBackend.controllers;


import com.melhamra.eatItBackend.dtos.UserDto;
import com.melhamra.eatItBackend.requests.UserRequest;
import com.melhamra.eatItBackend.responses.UserResponse;
import com.melhamra.eatItBackend.services.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private UserService userService;


    @PostMapping
    public ResponseEntity<UserResponse> createUser(@RequestBody @Valid UserRequest userRequest){
        UserDto userDto = modelMapper.map(userRequest, UserDto.class);
        UserDto user = userService.createUser(userDto);
        UserResponse createdUser = modelMapper.map(user, UserResponse.class);

        return ResponseEntity.status(HttpStatus.CREATED).body(createdUser);
    }

    @GetMapping
    public String hello(){
        return "Hello";
    }
}
