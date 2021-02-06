package com.melhamra.eatitbackend.requests;

import lombok.Data;

@Data
public class UserLoginRequest {

    private String email;
    private String password;

}
