package com.melhamra.eatItBackend.services;

import com.melhamra.eatItBackend.entities.OrderEntity;
import com.melhamra.eatItBackend.entities.OrderProductEntity;
import com.melhamra.eatItBackend.entities.ProductEntity;
import com.melhamra.eatItBackend.entities.UserEntity;
import com.melhamra.eatItBackend.exceptions.EatItException;
import com.melhamra.eatItBackend.repositories.OrderProductRepository;
import com.melhamra.eatItBackend.repositories.OrderRepository;
import com.melhamra.eatItBackend.repositories.ProductRepository;
import com.melhamra.eatItBackend.repositories.UserRepository;
import com.melhamra.eatItBackend.requests.OrderRequest;
import com.melhamra.eatItBackend.responses.OrderProductResponse;
import com.melhamra.eatItBackend.responses.OrderResponse;
import com.melhamra.eatItBackend.utils.IDGenerator;
import com.melhamra.eatItBackend.utils.OrderStatus;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Optional;

@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    ModelMapper modelMapper;
    @Autowired
    private OrderProductRepository orderProductRepository;
    @Autowired
    ProductRepository productRepository;
    @Autowired
    OrderRepository orderRepository;
    @Autowired
    UserRepository userRepository;
    @Autowired
    IDGenerator idGenerator;

    @Override
    public OrderResponse createOrder(OrderRequest orderRequest) {
        UserEntity user = userRepository.findByPublicId(orderRequest.getUserPublicId())
                .orElseThrow(() -> new EatItException("No user found with this id: " + orderRequest.getUserPublicId()));
        OrderEntity orderEntity =
                new OrderEntity(null, idGenerator.generateStringId(20),
                        orderRequest.getAddress(), Instant.now(), OrderStatus.PROCESSING, user, null);
        OrderEntity savedOrder = orderRepository.save(orderEntity);
        orderRepository.flush();

        OrderResponse orderResponse = new OrderResponse();
        orderResponse.setPublicId(savedOrder.getPublicId());
        orderResponse.setAddress(savedOrder.getAddress());
        orderResponse.setUserPublicId(user.getPublicId());

        orderRequest.getOrderProducts().forEach(orderProductRequest -> {
            ProductEntity product = productRepository.findByPublicId(orderProductRequest.getProductPublicId())
                    .orElseThrow(() -> new EatItException("No user found with this id: " + orderProductRequest.getProductPublicId()));
            OrderProductEntity orderProductEntity =
                    new OrderProductEntity(null, orderProductRequest.getQuantity(), savedOrder, product);
            OrderProductEntity savedOrderProductEntity = orderProductRepository.save(orderProductEntity);
            orderResponse.getOrderProducts()
                    .add(new OrderProductResponse(savedOrderProductEntity.getQuantity(), savedOrderProductEntity.getProduct().getPublicId()));
        });
        return orderResponse;
    }
}
