package com.melhamra.eatItBackend.services;

import com.melhamra.eatItBackend.dtos.UserDto;
import com.melhamra.eatItBackend.responses.OrderResponse;
import com.melhamra.eatItBackend.responses.UserByOrderStatisticsResponse;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;

public interface UserService extends UserDetailsService {

    UserDto createUser(UserDto userDto);

    UserDto getUserByEmail(String email);

    List<OrderResponse> getOrdersByUser(String publicId);

    UserDto getUserByPublicId(String publicId);
}
