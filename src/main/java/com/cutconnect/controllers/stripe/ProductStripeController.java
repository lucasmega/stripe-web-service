package com.cutconnect.controllers.stripe;

import com.cutconnect.services.stripe.CustomerStripeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.cutconnect.domains.stripe.ProductData;
import com.cutconnect.services.stripe.ProductStripeService;

import java.util.List;

@RestController
public class ProductStripeController {

    private final ProductStripeService productStripeService;

    private static final Logger logger = LoggerFactory.getLogger(CustomerStripeService.class);

    public ProductStripeController(ProductStripeService productStripeService) {
        this.productStripeService = productStripeService;
    }

    @PostMapping("/create-product")
    public ResponseEntity<ProductData> createProduct(@RequestBody ProductData productData) {
        try {
            return ResponseEntity.ok(productStripeService.createProduct(productData));
        } catch (Exception e) {
            logger.error("Erro ao criar produto: " + e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @GetMapping("/get-all-products")
    public ResponseEntity<List<ProductData>> getAllProducts() {
        try {
            return ResponseEntity.ok(productStripeService.getAllProducts("acct_1OpyCIQ2e9tLHH8u"));
        } catch (Exception e) {
            logger.error("Erro ao buscar produtos: " + e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }
    @DeleteMapping("/delete-product/{id}")
    public ResponseEntity<String> deleteProduct(@PathVariable("id") String productId) {
        try {
            return ResponseEntity.ok(productStripeService.deleteProduct(productId));
        } catch (Exception e) {
            logger.error("Erro ao deletar produto: " + e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @GetMapping("/get-product-by-id/{id}")
    public ResponseEntity<ProductData> getProductById(@PathVariable("id") String productId) {
        try {
            return ResponseEntity.ok(productStripeService.getProductById(productId));
        } catch (Exception e) {
            logger.error("Erro ao buscar produto: " + e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @PostMapping("/update-product")
    public ResponseEntity<ProductData> updateProduct(@RequestBody ProductData productData) {
        try {
            return ResponseEntity.ok(productStripeService.updateProduct(productData));
        } catch (Exception e) {
            logger.error("Erro ao atualizar produto: " + e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }
}
