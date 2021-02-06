package com.melhamra.eatitbackend.controllers;

import com.melhamra.eatitbackend.requests.OrderRequest;
import com.melhamra.eatitbackend.responses.OrderResponse;
import com.melhamra.eatitbackend.responses.UserByOrderStatisticsResponse;
import com.melhamra.eatitbackend.services.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

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

    @PatchMapping("/{orderPublicId}")
    public ResponseEntity<OrderResponse> updateStatus(@PathVariable String orderPublicId, @RequestParam String status){
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(orderService.updateStatus(orderPublicId, status));
    }

    @GetMapping("/ordersNumberByUser")
    public ResponseEntity<List<UserByOrderStatisticsResponse>> getOrderStatistics(){
        return ResponseEntity.ok(orderService.ordersNumberByUser());
    }


}
