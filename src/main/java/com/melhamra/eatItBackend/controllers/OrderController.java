package com.melhamra.eatItBackend.controllers;

import com.melhamra.eatItBackend.requests.OrderRequest;
import com.melhamra.eatItBackend.responses.OrderResponse;
import com.melhamra.eatItBackend.services.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/orders")
public class OrderController {

    @Autowired
    OrderService orderService;

    @PostMapping
    public ResponseEntity<OrderResponse> createOrder(@RequestBody @Valid OrderRequest orderRequest){
        return ResponseEntity.status(HttpStatus.CREATED).body(orderService.createOrder(orderRequest));
    }

    @GetMapping("/{orderPublicId}")
    public ResponseEntity<OrderResponse> getOrder(@PathVariable String orderPublicId){
        return ResponseEntity.ok(orderService.getOrderByPublicId(orderPublicId));
    }

}
