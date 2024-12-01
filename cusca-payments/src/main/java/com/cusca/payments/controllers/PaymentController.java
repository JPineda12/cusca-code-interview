package com.cusca.payments.controllers;

import com.cusca.payments.dto.request.PaymentRequestDto;
import com.cusca.payments.dto.response.PaymentResponseDto;
import com.cusca.payments.services.PaymentService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/payment")
@Validated
public class PaymentController {

    private final PaymentService paymentService;

    public PaymentController(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    @PostMapping("/process")
    public ResponseEntity<PaymentResponseDto> processPayment(@RequestHeader("Authorization") String token,
            @Valid @RequestBody PaymentRequestDto paymentRequest) {
        return ResponseEntity.ok(paymentService.processPayment(paymentRequest, token));
    }

}
