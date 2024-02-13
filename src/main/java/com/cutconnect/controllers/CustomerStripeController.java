package com.cutconnect.controllers;

import com.stripe.exception.StripeException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.cutconnect.domains.CustomerData;
import com.cutconnect.services.CustomerStripeService;

@RestController
public class CustomerStripeController {

    private final CustomerStripeService customerStripeService;
    private static final Logger logger = LoggerFactory.getLogger(CustomerStripeService.class);

    public CustomerStripeController(CustomerStripeService customerStripeService) {
        this.customerStripeService = customerStripeService;
    }

    @PostMapping("/create-customer")
    public ResponseEntity<CustomerData> createCustomer(@RequestBody CustomerData request) {
        try {
            return ResponseEntity.ok(customerStripeService.createCustomer(request));
        } catch (Exception e) {
            logger.error("Erro ao criar cliente: " + e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @GetMapping("/get-all-customer")
    public ResponseEntity<List<CustomerData>> getAllCustomer() {
        try {
            return ResponseEntity.ok(customerStripeService.getAllCustomers());
        } catch (Exception e) {
            logger.error("Erro ao buscar clientes: " + e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @DeleteMapping("/delete-customer/{id}")
    public ResponseEntity<String> deleteCustomer(@PathVariable("id") String id) {
        try {
            return ResponseEntity.ok(customerStripeService.deleteCustomer(id));
        } catch (Exception e) {
            logger.error("Erro ao deletar cliente: " + e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @GetMapping("get-customer-by-id/{id}")
    public ResponseEntity<CustomerData> getCustomerById(@PathVariable("id") String id) {
        try {
            return ResponseEntity.ok(customerStripeService.getCustomerById(id));
        } catch (Exception e) {
            logger.error("Erro ao buscar cliente: " + e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @PostMapping("/update-customer")
    public ResponseEntity<CustomerData> updateCustomer(@RequestBody CustomerData customerData) {
        try {
            return ResponseEntity.ok(customerStripeService.updateCustomer(customerData));
        } catch (Exception e) {
            logger.error("Erro ao atualizar dados do cliente: " + e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

}
