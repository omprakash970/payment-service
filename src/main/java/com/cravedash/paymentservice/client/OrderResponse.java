package com.cravedash.paymentservice.client;

public record OrderResponse(
        Long id,
        Long customerId,
        Long restaurantId,
        String status,
        Double totalAmount
) {}