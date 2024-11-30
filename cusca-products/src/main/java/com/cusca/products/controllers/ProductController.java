package com.cusca.products.controllers;

import com.cusca.products.models.ProductModel;
import com.cusca.products.services.ProductService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/products")
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping
    public List<ProductModel> listProducts(
            @RequestParam(value = "limit", required = false) Integer limit,
            @RequestParam(value = "sort", required = false) String sort
    ) {
        return productService.listProducts(limit, sort);
    }

    @GetMapping("/{id}")
    public ProductModel obtainProduct(@PathVariable Long id) {
        return productService.product(id);
    }

    @PostMapping
    public ResponseEntity<ProductModel> createProduct(@RequestBody ProductModel product) {
        ProductModel createdProduct = productService.createProduct(product);
        return new ResponseEntity<>(createdProduct, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProductModel> updateProduct(
            @PathVariable Long id,
            @RequestBody ProductModel updatedProduct) {
        ProductModel product = productService.updateProduct(id, updatedProduct);
        return ResponseEntity.ok(product);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, Object>> deleteProduct(@PathVariable Long id) {
        productService.deleteProduct(id);
        Map<String, Object> response = new HashMap<>();
        response.put("success", true);
        response.put("message", "Product with ID " + id + " deleted successfully.");
        return ResponseEntity.ok(response);
    }

    @GetMapping("/categories")
    public ResponseEntity<List<String>> getCategories(){
        return ResponseEntity.ok(productService.categories());
    }

    @GetMapping("/category/{category}")
    public ResponseEntity<List<ProductModel>> getProductByCategory(@PathVariable String category){
        return ResponseEntity.ok(productService.productsByCategory(category));
    }

}
