package com.melhamra.eatItBackend.responses;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderProductResponse {

    private int quantity;
    private double price;
    private String productPublicId;
    private String productName;

}
