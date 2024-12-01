package com.cusca.order.services;


import com.cusca.order.config.CustomException;
import com.cusca.order.dto.ProductDto;
import com.cusca.order.client.ProductsClient;
import com.cusca.order.dto.Response.OrderResponseDto;
import com.cusca.order.models.Order;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

@Service
public class OrderService {

    private final Map<Long, Order> orders = new HashMap<>();
    private final ProductsClient productsClient;

    public OrderService(ProductsClient productsClient) {
        this.productsClient = productsClient;
    }

    public OrderResponseDto addProductToOrder(Long userId, Long productId, String token) {
        ProductDto product = productsClient.getProductById(productId, token);

        if (product == null) {
            throw new CustomException(HttpStatus.BAD_REQUEST, "Product not found");
        }

        Order order = orders.getOrDefault(userId, new Order(userId, new ArrayList<>()));
        order.getProducts().add(product);
        order.setTotalAmount(order.getTotalAmount() + product.getPrice());
        orders.put(userId, order);

        return new OrderResponseDto("Product Successfully added", product);
    }

    public Order getOrder(Long userId) {
        return orders.getOrDefault(userId, new Order(userId, new ArrayList<>()));
    }
}
