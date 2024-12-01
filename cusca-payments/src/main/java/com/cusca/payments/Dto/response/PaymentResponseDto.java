package com.cusca.payments.dto.response;

import com.cusca.payments.dto.ProductDto;

import java.util.List;

public class PaymentResponseDto {

    private String message;
    private String paymentDate;
    private Long userId;
    private List<ProductDto> productList;
    private Double totalAmount;

    public PaymentResponseDto(String message, String paymentDate, Long userId, List<ProductDto> productList, Double totalAmount) {
        this.message = message;
        this.paymentDate = paymentDate;
        this.userId = userId;
        this.productList = productList;
        this.totalAmount = totalAmount;

    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getPaymentDate() {
        return paymentDate;
    }

    public void setPaymentDate(String paymentDate) {
        this.paymentDate = paymentDate;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public List<ProductDto> getProductList() {
        return productList;
    }

    public void setProductList(List<ProductDto> productList) {
        this.productList = productList;
    }

    public Double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(Double totalAmount) {
        this.totalAmount = totalAmount;
    }
}
