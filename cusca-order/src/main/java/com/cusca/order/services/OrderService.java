package com.cusca.order.services;


import com.cusca.order.Dto.ProductDto;
import com.cusca.order.client.ProductsClient;
import com.cusca.order.models.Order;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

@Service
public class OrderService {

    private Map<Long, Order> orders = new HashMap<>();
    private final ProductsClient productsClient;

    public OrderService(ProductsClient productsClient) {
        this.productsClient = productsClient;
    }

    public void addProductToOrder(Long userId, Long productId, String token) {
        ProductDto product = productsClient.getProductById(productId, token);

        if (product == null) {
            throw new RuntimeException("Producto no encontrado");
        }

        Order order = orders.getOrDefault(userId, new Order(userId, new ArrayList<>()));
        order.getProducts().add(product);
        order.setTotalAmount(order.getTotalAmount() + product.getPrice());
        orders.put(userId, order);
    }

    public Order getOrder(Long userId) {
        return orders.getOrDefault(userId, new Order(userId, new ArrayList<>()));
    }
}
