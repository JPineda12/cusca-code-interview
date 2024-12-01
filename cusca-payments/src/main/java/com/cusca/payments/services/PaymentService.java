package com.cusca.payments.services;

import com.cusca.payments.dto.request.PaymentRequestDto;
import com.cusca.payments.dto.response.PaymentResponseDto;
import com.cusca.payments.dto.ProductDto;
import com.cusca.payments.client.OrderClient;
import com.cusca.payments.config.CustomException;
import com.cusca.payments.utils.JwtUtil;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
public class PaymentService {


    private final OrderClient orderClient;
    private final JwtUtil jwtUtil;


    public PaymentService(OrderClient orderClient, JwtUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
        this.orderClient = orderClient;
    }

    public PaymentResponseDto processPayment(PaymentRequestDto paymentRequest, String token) {
        Long userId = jwtUtil.extractUserId(token);

        if (userId == null) {
            throw new CustomException(HttpStatus.BAD_REQUEST, "Invalid token");
        }

        List<ProductDto> productList = orderClient.getUserCart(userId, token);
        if (productList == null || productList.isEmpty()) {
            throw new CustomException(HttpStatus.BAD_REQUEST, "No items in cart");
        }

        String paymentDate = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));

        return new PaymentResponseDto(
                "Payment successful. Order is being shipped to " + paymentRequest.getShippingAddress(),
                paymentDate,
                userId,
                productList,
                productList.stream().mapToDouble(ProductDto::getPrice).sum()
        );
    }
}
