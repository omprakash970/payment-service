package com.cravedash.paymentservice.service;

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

    public PaymentService(PaymentRepository paymentRepository) {
        this.paymentRepository = paymentRepository;
    }

    public Payment createPayment(CreatePaymentRequest request) {

        Payment payment = Payment.builder()
                .orderId(request.orderId())

                .amount(request.amount())
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