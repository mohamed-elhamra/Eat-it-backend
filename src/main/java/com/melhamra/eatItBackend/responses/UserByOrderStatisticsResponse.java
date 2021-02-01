package com.melhamra.eatItBackend.responses;


public interface UserByOrderStatisticsResponse {

    String getClientPublicId();
    String getClientFullName();
    Long getNumberOfOrders();

}
