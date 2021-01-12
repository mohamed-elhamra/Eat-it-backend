package com.melhamra.eatItBackend.services;

import com.melhamra.eatItBackend.dtos.UserDto;
import com.melhamra.eatItBackend.entities.UserEntity;
import com.melhamra.eatItBackend.repositories.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Optional;


@SpringBootTest
class UserServiceImplTest {

    private static String email;
    private static UserEntity userEntity;
    private static UserDto userDto;

    @Autowired
    private UserService userService;

    @Autowired
    private ModelMapper modelMapper;

    @MockBean
    private UserRepository userRepository;

    @BeforeAll
    public static void init() {
        email = "mohamed@gmail.com";
        userEntity =
                new UserEntity(1L, "azdazda", "mohamed elhamra",
                        "mohamed@gmail.com", "066666666", "zefezfzef", "user");
        userDto =
                new UserDto(1L, "aabtert", "mohamed elhamra",
                        "mohamed@gmail.com", "067875599", "mohamed", "atrfedr", "user");
    }

    @Test
    void getUserByEmail() {
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
    void loadUserByUsernameTest() {
        Mockito.when(userRepository.findByEmail(email))
                .thenReturn(Optional.of(userEntity));
        Assertions.assertEquals(userEntity.getEmail(), userService.loadUserByUsername(email).getUsername());
    }
}