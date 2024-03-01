package com.cutconnect.controllers;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;

import com.cutconnect.domains.Schedule;
import com.cutconnect.services.ScheduleService;

@RestController
@RequestMapping(value = "/schedule")
public class ScheduleController {

    private static final Logger logger = LoggerFactory.getLogger(ScheduleController.class);

    private final ScheduleService scheduleService;

    @Autowired
    ScheduleController(ScheduleService scheduleService) {
        this.scheduleService = scheduleService;
    }

    @RequestMapping(value = "/find-all", method = RequestMethod.GET)
    public ResponseEntity<List<Schedule>> findAll() {
        try {
            return ResponseEntity.ok(this.scheduleService.findAll());
        } catch (Exception e) {
            logger.error("Erro ao buscar agendas: " + e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @RequestMapping(value = "/find-by-id/{id}", method = RequestMethod.GET)
    public ResponseEntity<Schedule> findById(@PathVariable("id") String id) {
        try {
            return ResponseEntity.ok(this.scheduleService.find(id));
        } catch (Exception e) {
            logger.error("Erro ao buscar agenda: " + e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public ResponseEntity<Schedule> save(@RequestBody Schedule schedule) {
        try {
            return ResponseEntity.ok(this.scheduleService.save(schedule));
        } catch (Exception e) {
            logger.error("Erro ao criar agenda: " + e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public ResponseEntity<Schedule> update(@RequestBody Schedule schedule) {
        try {
            return  ResponseEntity.ok(this.scheduleService.update(schedule));
        } catch (Exception e) {
            logger.error("Erro ao atualizar agenda: " + e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @RequestMapping(value = "/delete/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<Void> delete(@PathVariable String id) {
        try {
            scheduleService.delete(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            logger.error("Erro ao deletar agenda: " + e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }
}
