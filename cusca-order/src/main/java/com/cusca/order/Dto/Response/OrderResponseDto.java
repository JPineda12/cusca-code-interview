package com.cusca.order.dto.Response;

import com.cusca.order.dto.ProductDto;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class OrderResponseDto {

    private String message;
    private ProductDto product;
    private String timestamp;

    public OrderResponseDto(String message, ProductDto product) {
        this.message = message;
        this.product = product;
        this.timestamp = getFormattedTimestamp();
    }

    private String getFormattedTimestamp() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return LocalDateTime.now().format(formatter);
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public ProductDto getProduct() {
        return product;
    }

    public void setProduct(ProductDto product) {
        this.product = product;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }
}
