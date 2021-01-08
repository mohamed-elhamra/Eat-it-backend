package com.melhamra.eatItBackend.dtos;

import lombok.Data;


@Data
public class UserDto {

    private Long id;
    private String userId;
    private String fullName;
    private String email;
    private String phone;
    private String password;
    private String encryptedPassword;
    private String roles;

}
