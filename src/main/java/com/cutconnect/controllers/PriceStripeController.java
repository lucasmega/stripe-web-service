package com.cutconnect.controllers;

import com.cutconnect.services.CustomerStripeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.cutconnect.domains.PriceData;
import com.cutconnect.services.PriceStripeService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class PriceStripeController {

    private final PriceStripeService priceStripeService;
    private static final Logger logger = LoggerFactory.getLogger(CustomerStripeService.class);

    public PriceStripeController(PriceStripeService priceStripeService) {
        this.priceStripeService = priceStripeService;
    }

    @PostMapping("/create-recurring-price")
    public ResponseEntity<?> createRecurringPrice(@RequestBody PriceData priceData) {
        try {
            priceStripeService.createRecurringPrice(priceData);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            logger.error("Erro ao criar recorrencia de preço: " + e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @GetMapping("/get-all-princing")
    public ResponseEntity<?> getAllPricing() {
        try {
            return ResponseEntity.ok(priceStripeService.getAllPricing());
        } catch (Exception e) {
            logger.error("Erro ao criar preço: " + e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }
}
