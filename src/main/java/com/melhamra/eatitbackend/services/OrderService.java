package com.melhamra.eatitbackend.services;

import com.melhamra.eatitbackend.requests.OrderRequest;
import com.melhamra.eatitbackend.responses.OrderResponse;
import com.melhamra.eatitbackend.responses.UserByOrderStatisticsResponse;

import java.util.List;

public interface OrderService {

    OrderResponse createOrder(OrderRequest orderRequest);

    OrderResponse getOrderByPublicId(String publicId);

    OrderResponse updateStatus(String publicId, String status);

    List<UserByOrderStatisticsResponse> ordersNumberByUser();

}
