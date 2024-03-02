package com.cutconnect.controllers.stripe;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;

import com.cutconnect.services.AccountStripeService;
import com.cutconnect.services.stripe.CustomerStripeService;

@RestController
@RequestMapping("/account")
public class AccountStripeController {
    private static final Logger logger = LoggerFactory.getLogger(CustomerStripeService.class);

    private final AccountStripeService accountStripeService;
    @Autowired
    public AccountStripeController(AccountStripeService accountStripeService) {
        this.accountStripeService = accountStripeService;
    }

    @RequestMapping(value = "/create-account", method = RequestMethod.POST)
    public ResponseEntity<Map<String, Object>> createStripeAccount(@RequestBody String email) {
        try {
            return ResponseEntity.ok(accountStripeService.createStripeAccount(email));
        } catch (Exception e) {
            logger.error("Erro ao criar conta na stripe: " + e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @RequestMapping(value = "/delete-account/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<String> deleteStripeAccount(@PathVariable("id") String id) {
        try {
            return ResponseEntity.ok(accountStripeService.deleteStripeAccount(id));
        } catch (Exception e) {
            logger.error("Erro ao deletar conta stripe: " + e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }
}
