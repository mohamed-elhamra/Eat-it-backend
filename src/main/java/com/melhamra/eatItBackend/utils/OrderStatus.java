package com.melhamra.eatItBackend.utils;

import com.melhamra.eatItBackend.exceptions.EatItException;

import java.util.Arrays;

public enum OrderStatus {

    DELIVERED(1),
    PROCESSING(0),
    CANCELED(-1);

    private int status;

    OrderStatus(int status){
    }

    public Integer getStatus(){
        return status;
    }

    public static OrderStatus lookup(Integer status){
        return Arrays.stream(OrderStatus.values())
                .filter(value -> value.getStatus().equals(status))
                .findAny()
                .orElseThrow(() -> new EatItException("Order status not found"));
    }

}
