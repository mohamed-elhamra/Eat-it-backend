package com.melhamra.eatItBackend.responses;

public interface ProductStatisticsResponse {

    String getProductPublicId();
    Long getNumberOfCommand();
    Long getQuantity();

}
