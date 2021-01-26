package com.melhamra.eatItBackend.services;

import com.melhamra.eatItBackend.requests.OrderRequest;
import com.melhamra.eatItBackend.responses.OrderResponse;

public interface OrderService {

    OrderResponse createOrder(OrderRequest orderRequest);

    OrderResponse getOrderByPublicId(String publicId);

    OrderResponse updateStatus(String publicId, String status);

}
