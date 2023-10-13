package com.multipolar.bootcamp.product.controller;

import com.multipolar.bootcamp.product.domain.Product;
import com.multipolar.bootcamp.product.dto.ErrorMessage;
import com.multipolar.bootcamp.product.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/product")
public class ProductController {
    private final ProductService productService;
    @Autowired
    public ProductController(ProductService productService) {
        this.productService = productService;
    }
    @PostMapping
    public ResponseEntity<?> createProduct(@Valid @RequestBody Product product, BindingResult bindingResult){
        if (bindingResult.hasErrors()) {
            List<ErrorMessage> validationErrors = new ArrayList<>();
            for (FieldError error : bindingResult.getFieldErrors()) {
                ErrorMessage errorMessage = new ErrorMessage();
                errorMessage.setCode("VALIDATION_ERROR");
                errorMessage.setMessage(error.getDefaultMessage());
                validationErrors.add(errorMessage);
            }
            return ResponseEntity.badRequest().body(validationErrors);
        }
        Product createdProduct = productService.createOrUpdateProduct(product);
        return ResponseEntity.ok(createdProduct);
    }
    @GetMapping
    public List<Product> getAllProduct(){
        return productService.getAllProduct();
    }
    @GetMapping("/id/{id}")
    public ResponseEntity<Product>getProductById(@PathVariable String id){
        Optional<Product> product = productService.getProductById(id);
        return product.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }
    @PutMapping("/id/{id}")
    public Product updateProduct(@PathVariable String id, @RequestBody Product product){
        return productService.createOrUpdateProduct(product);
    }
    @DeleteMapping("/id/{id}")
    public ResponseEntity<Void> deleteCustomerById(@PathVariable String id){
        productService.deleteProductById(id);
        return ResponseEntity.noContent().build();
    }
}
