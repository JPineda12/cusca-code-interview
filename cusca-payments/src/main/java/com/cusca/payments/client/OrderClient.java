package com.cusca.payments.client;

import com.cusca.payments.config.CustomException;
import com.cusca.payments.dto.OrderDto;
import com.cusca.payments.dto.ProductDto;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Component
public class OrderClient {

    private final RestTemplate restTemplate;

    @Value("${orders.api.url}")
    private String ordersApiUrl;

    public OrderClient(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public List<ProductDto> getUserCart(Long userId, String token) {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", token);

        HttpEntity<String> entity = new HttpEntity<>(headers);

        RestTemplate restTemplate = new RestTemplate();
        try {
            ResponseEntity<OrderDto> response = restTemplate.exchange(this.ordersApiUrl, HttpMethod.GET, entity, OrderDto.class);

            if (response.getStatusCode().is2xxSuccessful()) {
                return response.getBody().getProducts();
            } else {
                throw new CustomException(HttpStatus.NOT_ACCEPTABLE, "Unsuccessful response from Products Api");
            }
        }catch(Exception e){
            throw new CustomException(HttpStatus.NOT_ACCEPTABLE, "Couldn't reach Orders Api, connection refused at"+ordersApiUrl);
        }
    }
}