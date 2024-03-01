package com.cutconnect.controllers;

import com.cutconnect.domains.Branch;
import com.cutconnect.services.BranchService;
import com.cutconnect.services.stripe.CustomerStripeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/branch")
public class BranchController {

    private BranchService branchService;

    private static final Logger logger = LoggerFactory.getLogger(BranchController.class);

    @Autowired
    public BranchController(BranchService branchService) {
        this.branchService = branchService;
    }

    @RequestMapping(value = "/find-all", method = RequestMethod.GET)
    public ResponseEntity<List<Branch>> findAll() {
        try {
            return ResponseEntity.ok(this.branchService.findAll());
        } catch (Exception e) {
            logger.error("Erro ao buscar filiais: " + e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @RequestMapping(value = "/find-by-id/{id}", method = RequestMethod.GET)
    public ResponseEntity<Branch> findById(@PathVariable("id") String id) {
        try {
            return ResponseEntity.ok(this.branchService.find(id));
        } catch (Exception e) {
            logger.error("Erro ao buscar filiar: " + e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @RequestMapping(value = "/create", method = RequestMethod.POST )
    public ResponseEntity<Branch> create(@RequestBody Branch branch) {
        try {
            return ResponseEntity.ok(this.branchService.save(branch));
        } catch (Exception e) {
            logger.error("Erro ao buscar filial: " + e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public ResponseEntity<Branch> update(@RequestBody Branch branch) {
        try {
            return ResponseEntity.ok(this.branchService.update(branch));
        } catch (Exception e) {
            logger.error("Erro ao atualizar filial: " + e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @RequestMapping(value = "/delete/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<Void> delete(@PathVariable String id) {
        try {
            branchService.delete(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            logger.error("Erro ao deletar filial: " + e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }
}
