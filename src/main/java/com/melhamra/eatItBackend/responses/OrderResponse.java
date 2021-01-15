package com.melhamra.eatItBackend.responses;

import com.melhamra.eatItBackend.requests.OrderProductRequest;
import com.melhamra.eatItBackend.utils.OrderStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderResponse {

    private String publicId;
    private String address;
    private OrderStatus status;
    private Instant date;
    private String userPublicId;
    private List<OrderProductResponse> orderProducts = new ArrayList<>();

}
