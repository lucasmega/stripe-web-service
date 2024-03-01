package com.cutconnect.controllers;

import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;

import com.cutconnect.domains.Professional;
import com.cutconnect.services.ProfessionalService;
import com.cutconnect.services.stripe.CustomerStripeService;

@RestController
@RequestMapping(value = "/professional")
public class ProfessionalController {
    private final ProfessionalService professionalService;

    private static final Logger logger = LoggerFactory.getLogger(ProfessionalController.class);

    @Autowired
    public ProfessionalController(ProfessionalService professionalService) {
        this.professionalService = professionalService;
    }

    @RequestMapping(value = "/find-all", method = RequestMethod.GET)
    public ResponseEntity<List<Professional>> findAll() {
        try {
            return ResponseEntity.ok(this.professionalService.findAll());
        } catch (Exception e) {
            logger.error("Erro ao buscar profissionais: " + e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @RequestMapping(value = "/find-by-id/{id}", method = RequestMethod.GET)
    public ResponseEntity<Professional> findById(@PathVariable("id") String id) {
        try {
            return ResponseEntity.ok(this.professionalService.find(id));
        } catch (Exception e) {
            logger.error("Erro ao buscar profissional: " + e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public ResponseEntity<Professional> save(@RequestBody Professional professional) {
        try {
            return ResponseEntity.ok(this.professionalService.save(professional));
        } catch (Exception e) {
            logger.error("Erro ao criar profissional: " + e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public ResponseEntity<Professional> update(@RequestBody Professional professional) {
        try {
            return ResponseEntity.ok(this.professionalService.update(professional));
        } catch (Exception e) {
            logger.error("Erro ao atualizar dados do professional: " + e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @RequestMapping(value = "/delete/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<Void> delete(@PathVariable String id) {
        try {
            professionalService.delete(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            logger.error("Erro ao deletar profissional: " + e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }
}
