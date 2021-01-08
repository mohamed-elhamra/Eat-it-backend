package com.melhamra.eatItBackend.services;

import com.melhamra.eatItBackend.dtos.UserDto;
import com.melhamra.eatItBackend.entities.UserEntity;
import com.melhamra.eatItBackend.repositories.UserRepository;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Optional;

import static org.mockito.Mockito.when;

@SpringBootTest
class UserServiceImplTest {

    @Autowired
    private UserService userService;

    @Autowired
    private ModelMapper modelMapper;

    @MockBean
    private UserRepository userRepository;

    @Test
    void getUserByEmail() {
        String email = "mohamed@gmail.com";
        UserEntity userEntity =
                new UserEntity(1L, "azdazda", "mohamed elhamra",
                        "mohamed@gmail.com", "066666666", "zefezfzef", "user");
        when(userRepository.findByEmail(email))
                .thenReturn(Optional.of(userEntity));
        assertEquals(modelMapper.map(userEntity, UserDto.class), userService.getUserByEmail(email));
    }
}