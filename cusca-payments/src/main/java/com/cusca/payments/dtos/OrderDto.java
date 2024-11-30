package com.cusca.payments.dtos;

import java.util.List;

public class OrderDto {
    private Long userId;
    private List<ProductDto> products;
    private Double totalAmount;

    public OrderDto(Long userId, List<ProductDto> products) {
        this.userId = userId;
        this.products = products;
        this.totalAmount = products.stream().mapToDouble(ProductDto::getPrice).sum();
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(Double totalAmount) {
        this.totalAmount = totalAmount;
    }

    public List<ProductDto> getProducts() {
        return products;
    }

    public void setProducts(List<ProductDto> products) {
        this.products = products;
    }
}