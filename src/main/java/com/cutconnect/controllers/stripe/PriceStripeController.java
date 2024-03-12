package com.cutconnect.controllers.stripe;

import com.cutconnect.domains.stripe.Cost;
import com.cutconnect.services.stripe.CustomerStripeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.cutconnect.domains.stripe.PriceData;
import com.cutconnect.services.stripe.PriceStripeService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping(value = "/price")
@RestController
public class PriceStripeController {

    private final PriceStripeService priceStripeService;
    private static final Logger logger = LoggerFactory.getLogger(CustomerStripeService.class);

    public PriceStripeController(PriceStripeService priceStripeService) {
        this.priceStripeService = priceStripeService;
    }
    @RequestMapping(value = "/create-recurring-price", method = RequestMethod.POST)
    public ResponseEntity<?> createRecurringPrice(@RequestBody PriceData priceData) {
        try {
            priceStripeService.createRecurringPrice(priceData);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            logger.error("Erro ao criar recorrencia de preço: " + e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }
    @RequestMapping(value = "/get-all-princing", method = RequestMethod.GET)
    public ResponseEntity<Cost> getAllPricing() {
        try {
            return ResponseEntity.ok(priceStripeService.getAllPricing("acct_1OpyCIQ2e9tLHH8u"));
        } catch (Exception e) {
            logger.error("Erro ao buscar preço: " + e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }
    @RequestMapping(value = "/get-price-by-product", method = RequestMethod.POST)
    public ResponseEntity<Cost> getPriceByProduct(@RequestBody String productId) {
        try {
            return ResponseEntity.ok(priceStripeService.getPriceByProduct(productId));
        } catch (Exception e) {
            logger.error("Erro ao buscar preço por produto: " + e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }
}
