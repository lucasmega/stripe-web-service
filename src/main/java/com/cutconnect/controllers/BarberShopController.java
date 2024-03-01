package com.cutconnect.controllers;

import java.net.URI;

import com.cutconnect.domains.BarberShop;
import com.cutconnect.services.BarberShopService;
import com.cutconnect.services.stripe.CustomerStripeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import spark.Response;

import java.util.List;

@RestController
@RequestMapping(value = "/barbershop")
public class BarberShopController {

    private BarberShopService barberShopService;

    private static final Logger logger = LoggerFactory.getLogger(CustomerStripeService.class);

    @Autowired
    public BarberShopController(BarberShopService barberShopService) {
        this.barberShopService = barberShopService;
    }

    @RequestMapping(value = "/find-all", method = RequestMethod.GET)
    public ResponseEntity<List<BarberShop>> findAll() {
        try {
            return ResponseEntity.ok(this.barberShopService.findAll());
        } catch (Exception e) {
            logger.error("Erro ao buscar barbearias: " + e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @RequestMapping(value = "/find-by-id/{id}", method = RequestMethod.GET)
    public ResponseEntity<BarberShop> findById(@PathVariable("id") String id) {
        try {
            return ResponseEntity.ok(this.barberShopService.find(id));
        } catch (Exception e) {
            logger.error("Erro ao buscar barbearia: " + e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public ResponseEntity<BarberShop> insert(@RequestBody BarberShop barberShop) {
        try {
            return ResponseEntity.ok(this.barberShopService.insert(barberShop));
        } catch (Exception e) {
            logger.error("Erro ao buscar barbearia: " + e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public ResponseEntity<BarberShop> update(@RequestBody BarberShop barberShop) {
        try {
            return ResponseEntity.ok(this.barberShopService.update(barberShop));
        } catch (Exception e) {
            logger.error("Erro ao buscar barbearia: " + e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

     @RequestMapping(value = "/delete/{id}", method = RequestMethod.DELETE)
     public ResponseEntity<Void> delete(@PathVariable String id) {
         try {
              barberShopService.delete(id);
              return ResponseEntity.noContent().build();
         } catch (Exception e) {
             logger.error("Erro ao buscar barbearia: " + e);
             return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
         }
     }

}
