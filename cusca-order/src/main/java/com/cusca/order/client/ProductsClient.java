package com.cusca.order.client;

import com.cusca.order.dto.ProductDto;
import com.cusca.order.config.CustomException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class ProductsClient {

    private final RestTemplate restTemplate;

    @Value("${products.api.url}")
    private String productsApiUrl;

    public ProductsClient(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public ProductDto getProductById(Long productId, String token) {

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", token);
        HttpEntity<String> entity = new HttpEntity<>(headers);
        String url = productsApiUrl + productId;
        ResponseEntity<ProductDto> response;
        try {
            response = restTemplate.exchange(url, HttpMethod.GET, entity, ProductDto.class);

            if (response.getStatusCode().is2xxSuccessful()) {
                return response.getBody();
            } else {
                throw new CustomException(HttpStatus.NOT_ACCEPTABLE, "Unsuccessful response from Orders Api");
            }
        } catch (Exception e) {
            throw new CustomException(HttpStatus.NOT_ACCEPTABLE, "Couldn't reach Products Api, connection refused at "+url);
        }
    }
}
