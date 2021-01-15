package com.melhamra.eatItBackend.requests;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderRequest {

    @NotBlank(message = "Address should not be empty")
    @Size(min = 5, max = 50, message = "Address should be between 5 and 50 character")
    private String address;
    @NotBlank(message = "User id should not be empty")
    private String userPublicId;
    private List<OrderProductRequest> orderProducts;

}
