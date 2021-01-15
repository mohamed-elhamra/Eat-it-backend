package com.melhamra.eatItBackend.responses;

import com.melhamra.eatItBackend.requests.OrderProductRequest;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderResponse {

    private String publicId;
    private String address;
    private String userPublicId;
    private List<OrderProductResponse> orderProducts = new ArrayList<>();

}
