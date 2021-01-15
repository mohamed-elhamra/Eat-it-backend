package com.melhamra.eatItBackend.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {

    private Long id;
    private String publicId;
    private String fullName;
    private String email;
    private String phone;
    private String password;
    private String encryptedPassword;
    private String roles;

}
