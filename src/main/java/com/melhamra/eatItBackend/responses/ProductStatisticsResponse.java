package com.melhamra.eatItBackend.responses;

public interface ProductStatisticsResponse {

    String getProductPublicId();
    String getProductName();
    Long getNumberOfCommand();
    Long getQuantity();

}
