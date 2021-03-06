package com.melhamra.eatitbackend.controllers;


import com.melhamra.eatitbackend.dtos.UserDto;
import com.melhamra.eatitbackend.requests.UserRequest;
import com.melhamra.eatitbackend.responses.OrderResponse;
import com.melhamra.eatitbackend.responses.UserResponse;
import com.melhamra.eatitbackend.services.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

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

    @GetMapping("/{publicId}/orders")
    public ResponseEntity<List<OrderResponse>> getOrdersByUser(@PathVariable String publicId){
        return ResponseEntity.ok(userService.getOrdersByUser(publicId));
    }

    @GetMapping("/{publicId}")
    public ResponseEntity<UserResponse> getUserByPublicId(@PathVariable String publicId){
        UserDto userDto = userService.getUserByPublicId(publicId);
        return ResponseEntity.ok(modelMapper.map(userDto, UserResponse.class));
    }

    @GetMapping("/email/{email}")
    public ResponseEntity<UserResponse> getUserByEmail(@PathVariable("email") String email){
        UserDto userDto = userService.getUserByEmail(email);
        return ResponseEntity.ok(modelMapper.map(userDto, UserResponse.class));
    }

    @GetMapping
    public String hello(){
        return "Hello";
    }
}
