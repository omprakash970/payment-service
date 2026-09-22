package com.cravedash.paymentservice.client;

import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class OrderClient {

    private final RestTemplate restTemplate;
    private static final String BASE_URL = "http://localhost:8082";

    public OrderClient() {
        this.restTemplate = new RestTemplate();
    }

    public OrderResponse getOrder(Long orderId) {
        return restTemplate.getForObject(BASE_URL + "/orders/{id}", OrderResponse.class, orderId);
    }
}