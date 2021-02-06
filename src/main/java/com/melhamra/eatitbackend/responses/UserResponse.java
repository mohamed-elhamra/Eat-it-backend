package com.melhamra.eatitbackend.responses;

import lombok.Data;

@Data
public class UserResponse {

    private String userId;
    private String fullName;
    private String email;
    private String phone;

}
