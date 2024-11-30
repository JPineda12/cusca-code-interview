package com.cusca.payments.services;

import com.cusca.payments.Dto.PaymentRequestDto;
import org.springframework.stereotype.Service;

@Service
public class PaymentService {


    public boolean processPayment(PaymentRequestDto paymentRequest, Long userId, Object orderDetails) {
        return true;  // Simulacion del pago retorna true por defecto
    }
}
