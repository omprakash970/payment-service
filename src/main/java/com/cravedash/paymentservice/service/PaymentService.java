package com.cravedash.paymentservice.service;

import com.cravedash.paymentservice.client.OrderClient;
import com.cravedash.paymentservice.client.OrderResponse;
import com.cravedash.paymentservice.dto.CreatePaymentRequest;
import com.cravedash.paymentservice.entity.Payment;
import com.cravedash.paymentservice.entity.PaymentStatus;
import com.cravedash.paymentservice.repository.PaymentRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
public class PaymentService {

    private final PaymentRepository paymentRepository;
    private final OrderClient orderClient;

    public PaymentService(
            PaymentRepository paymentRepository,
            OrderClient orderClient) {

        this.paymentRepository = paymentRepository;
        this.orderClient = orderClient;
    }

    public Payment createPayment(CreatePaymentRequest request) {

        // Get order details from Order Service
        OrderResponse order =
                orderClient.getOrder(request.orderId());

        if (order == null) {
            throw new RuntimeException("Order not found");
        }

        // Get the amount from the Order Service
        Double amount = order.totalAmount();

        Payment payment = Payment.builder()
                .orderId(order.id())
                .amount(amount)
                .transactionId(
                        "TXN_" + UUID.randomUUID()
                )
                .status(PaymentStatus.PENDING)
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();

        return paymentRepository.save(payment);
    }

    public Payment getPayment(Long id) {

        return paymentRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException(
                                "Payment not found"
                        ));
    }

    public Payment getPaymentByOrder(Long orderId) {

        return paymentRepository.findByOrderId(orderId)
                .orElseThrow(() ->
                        new RuntimeException(
                                "Payment not found for order"
                        ));
    }

    public List<Payment> getAllPayments() {
        return paymentRepository.findAll();
    }

    public Payment updateStatus(
            Long id,
            PaymentStatus status) {

        Payment payment = getPayment(id);

        payment.setStatus(status);
        payment.setUpdatedAt(LocalDateTime.now());

        return paymentRepository.save(payment);
    }
}