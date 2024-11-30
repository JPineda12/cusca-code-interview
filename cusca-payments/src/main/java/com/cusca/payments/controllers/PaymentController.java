package com.cusca.payments.controllers;

import com.cusca.payments.client.OrderClient;
import com.cusca.payments.dtos.PaymentRequestDto;
import com.cusca.payments.dtos.PaymentResponseDto;
import com.cusca.payments.dtos.ProductDto;
import com.cusca.payments.services.PaymentService;
import com.cusca.payments.utils.JwtUtil;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@RestController
@RequestMapping("/api/payment")
public class PaymentController {

    private final PaymentService paymentService;

    private final OrderClient orderClient;

    private final JwtUtil jwtUtil;

    public PaymentController(PaymentService paymentService, OrderClient orderClient, JwtUtil jwtUtil) {
        this.paymentService = paymentService;
        this.jwtUtil = jwtUtil;
        this.orderClient = orderClient;
    }

    @PostMapping("/process")
    public ResponseEntity<PaymentResponseDto> processPayment(@RequestHeader("Authorization") String token,
            @RequestBody PaymentRequestDto paymentRequest) {

        Long userId = jwtUtil.extractUserId(token);

        if (userId == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new PaymentResponseDto("Invalid token", null, null, null, null));
        }

        List<ProductDto> productList = orderClient.getUserCart(userId, token);

        if (productList == null || productList.isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new PaymentResponseDto("No items in cart", null, userId, null, null));
        }

        boolean paymentSuccess = paymentService.processPayment(paymentRequest, userId, productList);

        if (paymentSuccess) {
            String paymentDate = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));

            return ResponseEntity.ok(new PaymentResponseDto(
                    "Payment successful. Order is being shipped to " + paymentRequest.getShippingAddress(),
                    paymentDate,
                    userId,
                    productList,
                    productList.stream().mapToDouble(ProductDto::getPrice).sum()  // Sum the total amount
            ));
        } else {
            return ResponseEntity.status(HttpStatus.PAYMENT_REQUIRED).body(new PaymentResponseDto("Payment failed", null, userId, null, null));
        }
    }
}
