package com.cusca.products.services;

import com.cusca.products.models.ProductModel;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Arrays;
import java.util.List;

@Service
public class ProductService {

    private final RestTemplate restTemplate;
    private final String API_URL = "https://fakestoreapi.com/products";

    public ProductService(RestTemplateBuilder restTemplate) {
        this.restTemplate = restTemplate.build();
    }

    public List<ProductModel> listProducts(Integer limit, String sort) {
        String url = UriComponentsBuilder
                .fromUriString(API_URL)
                .queryParam("limit", limit)
                .queryParam("sort", sort)
                .toUriString();

        ProductModel[] products = restTemplate.getForObject(url, ProductModel[].class);
        return Arrays.asList(products);
    }

    public ProductModel product(Long id) {
        return restTemplate.getForObject(API_URL + "/" + id, ProductModel.class);
    }


    public ProductModel createProduct(ProductModel entity){

        return restTemplate.postForObject(API_URL, entity, ProductModel.class);
    }

    public ProductModel updateProduct(Long id, ProductModel updatedEntity){
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<ProductModel> requestEntity = new HttpEntity<>(updatedEntity, headers);
        ResponseEntity<ProductModel> responseEntity =
                restTemplate.exchange(API_URL+"/"+id, HttpMethod.PUT, requestEntity, ProductModel.class);

        return responseEntity.getBody();
    }

    public Boolean deleteProduct(Long id){
        try {
            restTemplate.delete(API_URL + "/" + id);
            return true;
        }
        catch(HttpClientErrorException e) {
            throw new IllegalArgumentException("Product with ID " + id + " not found.");
        }
    }

    public List<String> categories(){
        String[] categories = restTemplate.getForObject(API_URL+"/categories", String[].class);
        return Arrays.asList(categories);
    }

    public List<ProductModel> productsByCategory(String categoryName){
        ProductModel[] products = restTemplate.getForObject(API_URL+"/category/"+categoryName, ProductModel[].class);
        return Arrays.asList(products);
    }
}

