package com.melhamra.eatitbackend.responses;

public interface ProductStatisticsResponse {

    String getProductPublicId();
    String getProductName();
    Long getNumberOfCommand();
    Long getQuantity();

}
