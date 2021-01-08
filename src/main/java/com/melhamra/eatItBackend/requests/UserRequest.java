package com.melhamra.eatItBackend.requests;

import lombok.Data;

import javax.validation.constraints.*;

@Data
public class UserRequest {

    @NotBlank(message = "Full name should not be empty")
    @Size(min = 5, max = 20, message = "Full name should be between 3 and 20 character")
    private String fullName;
    @Email(message = "Email format is incorrect")
    @NotNull(message = "Email should not be null")
    private String email;
    @Pattern(
            regexp = "^(?:(?:(?:\\+|00)212[\\s]?(?:[\\s]?\\(0\\)[\\s]?)?)|0){1}(?:5[\\s.-]?[2-3]|6[\\s.-]?[13-9]){1}[0-9]{1}(?:[\\s.-]?\\d{2}){3}$",
            message = "Phone number format is incorrect"
    )
    private String phone;
    @NotNull(message = "Password name should not be null")
    @Size(min = 8, max = 20, message = "Size should be between 8 and 20 character")
    private String password;
    private String roles;

}
