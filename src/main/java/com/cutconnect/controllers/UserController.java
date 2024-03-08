package com.cutconnect.controllers;

import java.util.List;

import com.cutconnect.domains.form.FavoriteBarbershop;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;

import com.cutconnect.domains.User;
import com.cutconnect.services.UserService;
import com.cutconnect.services.stripe.CustomerStripeService;

@RestController
@RequestMapping(value = "/user")
public class UserController {
    private static final Logger logger = LoggerFactory.getLogger(CustomerStripeService.class);

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @RequestMapping(value = "/find-all", method = RequestMethod.GET)
    public ResponseEntity<List<User>> findAll() {
        try {
            return ResponseEntity.ok(this.userService.findAll());
        } catch (Exception e) {
            logger.error("Erro ao buscar usuários: " + e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @RequestMapping(value = "/find-by-id/{id}", method = RequestMethod.GET)
    public ResponseEntity<User> findById(@PathVariable("id") String id) {
        try {
            return ResponseEntity.ok(userService.find(id));
        } catch (Exception e) {
            logger.error("Erro ao buscar usuário: " + e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public ResponseEntity<User> save(@RequestBody User user) {
        try {
            return ResponseEntity.ok(userService.save(user));
        } catch (Exception e) {
            logger.error("Erro ao buscar usuário: " + e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public ResponseEntity<User> update(@RequestBody User user) {
        try {
            return ResponseEntity.ok(this.userService.update(user));
        } catch (Exception e) {
            logger.error("Erro ao atualizar usuário: " + e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @RequestMapping(value = "/delete/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<Void> delete(@PathVariable String id) {
        try {
            this.userService.delete(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            logger.error("Erro ao deletar usuário: " + e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @RequestMapping(value = "/add-favorite-barbershop", method = RequestMethod.POST)
    public ResponseEntity<Void> addFavoriteBarbershop(@RequestBody FavoriteBarbershop object) {
        try {
            userService.addFavoriteBarbershop(object);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            logger.error("Erro ao favoritar barbearia: " + e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public ResponseEntity<Void> register(@RequestBody User user) {
        try {
            userService.register(user);
           return ResponseEntity.noContent().build();
        } catch (Exception e) {
            logger.error("Erro ao registrar usuário: " + e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }
}
