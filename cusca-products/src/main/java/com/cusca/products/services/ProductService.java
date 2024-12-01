package com.cusca.products.services;

import com.cusca.products.config.CustomException;
import com.cusca.products.models.ProductModel;
import org.springframework.beans.factory.annotation.Value;
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
    @Value("${fakestore.api.url}")
    private String FAKE_STORE_API_URL;

    public ProductService(RestTemplateBuilder restTemplate) {
        this.restTemplate = restTemplate.build();
    }

    public List<ProductModel> listProducts(Integer limit, String sort) {
        String url = UriComponentsBuilder
                .fromUriString(FAKE_STORE_API_URL)
                .queryParam("limit", limit)
                .queryParam("sort", sort)
                .toUriString();

        ProductModel[] products = restTemplate.getForObject(url, ProductModel[].class);
        return Arrays.asList(products);
    }

    public ProductModel product(Long id) {
        try {
            ProductModel product = restTemplate.getForObject(FAKE_STORE_API_URL + "/" + id, ProductModel.class);

            if (product.getId() == null || product.getId() <= 0) {
                throw new CustomException(HttpStatus.INTERNAL_SERVER_ERROR, "Product with id " + id + " not found");
            }
            return restTemplate.getForObject(FAKE_STORE_API_URL + "/" + id, ProductModel.class);
        }catch(Exception e){
            throw new CustomException(HttpStatus.NOT_FOUND, "Product with id " + id + " not found");
        }
    }


    public ProductModel createProduct(ProductModel entity){

        return restTemplate.postForObject(FAKE_STORE_API_URL, entity, ProductModel.class);
    }

    public ProductModel updateProduct(Long id, ProductModel updatedEntity){
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<ProductModel> requestEntity = new HttpEntity<>(updatedEntity, headers);
        ResponseEntity<ProductModel> responseEntity =
                restTemplate.exchange(FAKE_STORE_API_URL+"/"+id, HttpMethod.PUT, requestEntity, ProductModel.class);

        return responseEntity.getBody();
    }

    public Boolean deleteProduct(Long id){
        try {
            restTemplate.delete(FAKE_STORE_API_URL + "/" + id);
            return true;
        }
        catch(HttpClientErrorException e) {
            throw new CustomException(HttpStatus.BAD_REQUEST,"Product with ID " + id + " not found.");
        }
    }

    public List<String> categories(){
        String[] categories = restTemplate.getForObject(FAKE_STORE_API_URL+"/categories", String[].class);
        return Arrays.asList(categories);
    }

    public List<ProductModel> productsByCategory(String categoryName){
        ProductModel[] products = restTemplate.getForObject(FAKE_STORE_API_URL+"/category/"+categoryName, ProductModel[].class);
        return Arrays.asList(products);
    }
}

