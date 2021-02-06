package com.melhamra.eatitbackend.responses;


public interface UserByOrderStatisticsResponse {

    String getClientPublicId();
    String getClientFullName();
    Long getNumberOfOrders();

}
