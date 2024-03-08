package com.cutconnect.controllers.stripe;

import com.cutconnect.domains.User;
import com.cutconnect.domains.dto.ProductWithPriceDTO;
import com.cutconnect.services.UserService;
import com.cutconnect.services.stripe.CustomerStripeService;
import com.stripe.exception.StripeException;
import com.stripe.model.Product;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.cutconnect.domains.stripe.ProductData;
import com.cutconnect.services.stripe.ProductStripeService;

import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping(value = "/product")
public class ProductStripeController {

    private final ProductStripeService productStripeService;

    private final UserService userService;

    private static final Logger logger = LoggerFactory.getLogger(CustomerStripeService.class);

    @Autowired
    public ProductStripeController(ProductStripeService productStripeService, UserService userService) {
        this.productStripeService = productStripeService;
        this.userService = userService;
    }

    @RequestMapping(value = "/create-product", method = RequestMethod.POST)
    public ResponseEntity<ProductData> createProduct(@RequestBody ProductData productData) {
        try {
            return ResponseEntity.ok(productStripeService.createProduct(productData));
        } catch (Exception e) {
            logger.error("Erro ao criar produto: " + e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @RequestMapping(value = "/get-all-products", method = RequestMethod.GET)
    public ResponseEntity<List<ProductData>> getAllProducts() {
        try {
            return ResponseEntity.ok(productStripeService.getAllProducts("acct_1OpyCIQ2e9tLHH8u"));
        } catch (Exception e) {
            logger.error("Erro ao buscar produtos: " + e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }
    @RequestMapping(value = "/delete-product/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<String> deleteProduct(@PathVariable("id") String productId) {
        try {
            return ResponseEntity.ok(productStripeService.deleteProduct(productId));
        } catch (Exception e) {
            logger.error("Erro ao deletar produto: " + e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @RequestMapping(value = "/get-product-by-id/{id}", method = RequestMethod.GET)
    public ResponseEntity<ProductData> getProductById(@PathVariable("id") String productId) {
        try {
            return ResponseEntity.ok(productStripeService.getProductById(productId));
        } catch (Exception e) {
            logger.error("Erro ao buscar produto: " + e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @RequestMapping(value = "/update-product", method = RequestMethod.POST)
    public ResponseEntity<ProductData> updateProduct(@RequestBody ProductData productData) {
        try {
            return ResponseEntity.ok(productStripeService.updateProduct(productData));
        } catch (Exception e) {
            logger.error("Erro ao atualizar produto: " + e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @RequestMapping(value = "/all-products-from-connected-accounts", method = RequestMethod.GET)
    public ResponseEntity<List<Product>> getAllProductsFromConnectedAccounts() {
        try {
            return ResponseEntity.ok(productStripeService.getAllProductsFromConnectedAccounts());
        } catch (Exception e) {
            logger.error("Erro ao atualizar produto: " + e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @RequestMapping(value = "/find-products-by-email", method = RequestMethod.POST)
    public ResponseEntity<List<ProductWithPriceDTO>> getProductsByEmail(@RequestBody String email) throws StripeException {
        try {
            User user = userService.findByEmail(email);

            if (user == null) {
                throw new IllegalArgumentException("Usuário não encontrado");
            }

            if (user.getBarbershopId() == null || Objects.equals(user.getBarbershopId(), "")) {
                return ResponseEntity.ok(productStripeService.getProductsWithPrice());
            }

            return ResponseEntity.ok(productStripeService.getProductsWithPriceFromAccount(user.getAccountId()));
        } catch (Exception e) {
            logger.error("Erro ao buscar produtos: " + e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

}
