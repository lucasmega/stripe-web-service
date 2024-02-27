package com.cutconnect.controllers;

import com.cutconnect.domains.BarberShop;
import com.cutconnect.services.BarberShopService;
import com.cutconnect.services.stripe.CustomerStripeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class BarberShopController {

    private BarberShopService barberShopService;

    private static final Logger logger = LoggerFactory.getLogger(CustomerStripeService.class);

    @Autowired
    public BarberShopController(BarberShopService barberShopService) {
        this.barberShopService = barberShopService;
    }

    @GetMapping("/find-all")
    public ResponseEntity<List<BarberShop>> findAll() {
        try {
            return ResponseEntity.ok(this.barberShopService.findAll());
        } catch (Exception e) {
            logger.error("Erro ao buscar barbearias: " + e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

}
