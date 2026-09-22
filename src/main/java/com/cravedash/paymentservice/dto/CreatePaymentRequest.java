package com.cravedash.paymentservice.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record CreatePaymentRequest(

        @NotNull
        Long orderId,

        @NotNull
        @Positive
        Double amount

) {}