package com.melhamra.eatItBackend.requests;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderProductRequest {

    @Min(value = 1, message = "Quantity must be equal or greater than 1")
    private int quantity;
    @NotBlank(message = "Product id should not be empty")
    private String productPublicId;

}
