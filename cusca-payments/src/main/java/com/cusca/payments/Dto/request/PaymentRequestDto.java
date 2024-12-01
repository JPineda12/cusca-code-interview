package com.cusca.payments.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class PaymentRequestDto {

    @NotBlank(message = "Payment method cannot be blank")
    @NotNull(message = "Payment cannot be null")
    private String paymentMethod;

    @NotBlank(message = "Shipping address cannot be blank")
    @NotNull(message = "Shipping address be null")
    @Size(max=50)
    private String shippingAddress;

    public String getPaymentMethod() {
        return paymentMethod;
    }
    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public String getShippingAddress() {
        return shippingAddress;
    }

    public void setShippingAddress(String shippingAddress) {
        this.shippingAddress = shippingAddress;
    }
}
