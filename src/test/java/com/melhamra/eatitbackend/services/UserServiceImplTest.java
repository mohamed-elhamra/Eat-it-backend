package com.melhamra.eatitbackend.services;

import com.melhamra.eatitbackend.dtos.UserDto;
import com.melhamra.eatitbackend.entities.*;
import com.melhamra.eatitbackend.exceptions.EatItException;
import com.melhamra.eatitbackend.repositories.OrderProductRepository;
import com.melhamra.eatitbackend.repositories.OrderRepository;
import com.melhamra.eatitbackend.repositories.UserRepository;
import com.melhamra.eatitbackend.responses.OrderProductResponse;
import com.melhamra.eatitbackend.responses.OrderResponse;
import com.melhamra.eatitbackend.utils.OrderStatus;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.time.Instant;
import java.util.*;


@SpringBootTest
class UserServiceImplTest {

    private static String email;
    private static UserEntity userEntity;
    private static UserDto userDto;
    private static OrderEntity orderEntity;
    private static OrderProductEntity orderProductEntity1;
    private static OrderProductEntity orderProductEntity2;

    @Autowired
    private UserService userService;

    @Autowired
    private ModelMapper modelMapper;

    @MockBean
    private UserRepository userRepository;

    @MockBean
    private OrderRepository orderRepository;

    @MockBean
    private OrderProductRepository orderProductRepository;

    @BeforeAll
    public static void init() {
        email = "mohamed@gmail.com";

        CategoryEntity categoryEntity = new CategoryEntity(1L, "dzeff", "Pizza", null, null);

        userEntity = new UserEntity(1L, "mazda", "mohamed elhamra",
                "mohamed@gmail.com", "066666666", "zefezfzef", "user", null);
        userDto = new UserDto(1L, "afternoon", "mohamed elhamra",
                "mohamed@gmail.com", "067875599", "mohamed", "atrfedr", "user");
        orderEntity = new OrderEntity(1L, "adzdaed", "Rue Tanger Madrid",
                Instant.now(), OrderStatus.DELIVERED, userEntity, null);
        userEntity.setOrders(Collections.singletonList(orderEntity));

        ProductEntity productEntity1 = new ProductEntity(1L, "azdazd", "Margarita Pizza",
                "Delicious Pizza just try it!", 3, null, null, categoryEntity);
        ProductEntity productEntity2 = new ProductEntity(2L, "azdzezd", "Cheese Pizza",
                "Delicious Pizza just try it!", 7, null, null, categoryEntity);

        orderProductEntity1 = new OrderProductEntity(1L, 3, 3, orderEntity, productEntity1);
        orderProductEntity2 = new OrderProductEntity(2L, 4, 7, orderEntity, productEntity2);

        orderEntity.setOrderProducts(Arrays.asList(orderProductEntity1, orderProductEntity2));
        productEntity1.setOrderProducts(Collections.singletonList(orderProductEntity1));
        productEntity2.setOrderProducts(Collections.singletonList(orderProductEntity2));
        categoryEntity.setProducts(Arrays.asList(productEntity1, productEntity2));
    }

    @Test
    void getUserByEmailTest() {
        Mockito.when(userRepository.findByEmail(email))
                .thenReturn(Optional.of(userEntity));
        Assertions.assertEquals(modelMapper.map(userEntity, UserDto.class), userService.getUserByEmail(email));
    }

    @Test
    void createUserTest() {
        Mockito.when(userRepository.findByEmail(userDto.getEmail()))
                .thenReturn(Optional.empty());
        Mockito.when(userRepository.findByPhone(userDto.getPhone()))
                .thenReturn(Optional.empty());

        UserEntity userEntity = modelMapper.map(userDto, UserEntity.class);
        Mockito.when(userRepository.save(Mockito.any(UserEntity.class))).thenReturn(userEntity);

        Assertions.assertEquals(userDto.getEmail(), userService.createUser(userDto).getEmail());
    }

    @Test
    void createUserTest_WhenAlreadyUserExitsWithEmail() {
        Mockito.when(userRepository.findByEmail(userDto.getEmail()))
                .thenReturn(Optional.of(modelMapper.map(userDto, UserEntity.class)));

        Exception exception = Assertions.assertThrows(EatItException.class, () -> userService.createUser(userDto));

        String expectedMessage = "User already exits with this email";
        String actualMessage = exception.getMessage();
        Assertions.assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    void createUserTest_WhenAlreadyUserExitsWithPhone() {
        Mockito.when(userRepository.findByPhone(userDto.getPhone()))
                .thenReturn(Optional.of(modelMapper.map(userDto, UserEntity.class)));

        Exception exception = Assertions.assertThrows(EatItException.class, () -> userService.createUser(userDto));

        String expectedMessage = "User already exits with this phone";
        String actualMessage = exception.getMessage();
        Assertions.assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    void loadUserByUsernameTest() {
        Mockito.when(userRepository.findByEmail(email))
                .thenReturn(Optional.of(userEntity));
        Assertions.assertEquals(userEntity.getEmail(), userService.loadUserByUsername(email).getUsername());
    }

    @Test
    void loadUserByUsernameTest_WhenNoUserFound() {
        Exception exception = Assertions
                .assertThrows(UsernameNotFoundException.class, () -> userService.loadUserByUsername("moha@gmail.com"));

        String expectedMessage = "User not found with this email: " + "moha@gmail.com";
        String actualMessage = exception.getMessage();
        Assertions.assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    void getUserByPublicIdTest() {
        Mockito.when(userRepository.findByPublicId("mazda"))
                .thenReturn(Optional.of(userEntity));
        Assertions.assertEquals(userEntity.getId(), userService.getUserByPublicId("mazda").getId());
    }

    @Test
    void getUserByPublicIdTest_WhenNoUserFound() {
        Exception exception = Assertions.assertThrows(EatItException.class, () -> userService.getUserByPublicId("aaaaa"));

        String expectedMessage = "User not found with this id: " + "aaaaa";
        String actualMessage = exception.getMessage();
        Assertions.assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    void getOrdersByUserTest() {
        Mockito.when(userRepository.findByPublicId("mazda"))
                .thenReturn(Optional.of(userEntity));
        Mockito.when(orderRepository.findByUser(Mockito.any(UserEntity.class)))
                .thenReturn(Collections.singletonList(orderEntity));
        Mockito.when(orderProductRepository.findByOrder(Mockito.any(OrderEntity.class)))
                .thenReturn(Arrays.asList(orderProductEntity1, orderProductEntity2));

        List<OrderResponse> orderResponses = new ArrayList<>();
        userEntity.getOrders().forEach(order -> {
            OrderResponse orderResponse = new OrderResponse();
            orderResponse.setPublicId(order.getPublicId());
            orderResponse.setAddress(order.getAddress());
            orderResponse.setDate(order.getDate());
            orderResponse.setStatus(order.getStatus());
            orderResponse.setUserPublicId("mazda");

            orderEntity.getOrderProducts().forEach(orderProductEntity -> orderResponse.getOrderProducts()
                    .add(new OrderProductResponse(orderProductEntity.getQuantity(),
                            orderProductEntity.getPrice(),
                            orderProductEntity.getProduct().getPublicId(),
                            orderProductEntity.getProduct().getName())));
            orderResponses.add(orderResponse);
        });

        Assertions.assertEquals(orderResponses.size(), userService.getOrdersByUser("mazda").size());
        Assertions.assertEquals(orderResponses, userService.getOrdersByUser("mazda"));
    }

    @Test
    void getOrdersByUserTest_WhenNoUserFound() {
        Exception exception = Assertions.assertThrows(EatItException.class, () -> userService.getOrdersByUser("mazda"));

        String expectedMessage = "User not found with this id: " + "mazda";
        String actualMessage = exception.getMessage();
        Assertions.assertTrue(actualMessage.contains(expectedMessage));
    }

}