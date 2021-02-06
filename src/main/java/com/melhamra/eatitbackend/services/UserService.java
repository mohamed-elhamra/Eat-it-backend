package com.melhamra.eatitbackend.services;

import com.melhamra.eatitbackend.dtos.UserDto;
import com.melhamra.eatitbackend.responses.OrderResponse;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;

public interface UserService extends UserDetailsService {

    UserDto createUser(UserDto userDto);

    UserDto getUserByEmail(String email);

    List<OrderResponse> getOrdersByUser(String publicId);

    UserDto getUserByPublicId(String publicId);
}
