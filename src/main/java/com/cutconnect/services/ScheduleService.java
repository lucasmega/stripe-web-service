package com.cutconnect.services;

import com.cutconnect.domains.Schedule;
import com.cutconnect.repositories.ScheduleRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ScheduleService {

    private final ScheduleRepository scheduleRepository;

    @Autowired
    public ScheduleService(ScheduleRepository scheduleRepository) {
        this.scheduleRepository = scheduleRepository;
    }

    public List<Schedule> findAll() {
        return  scheduleRepository.findAll();
    }

    public Schedule find(String id) {
        Optional<Schedule> object = scheduleRepository.findById(id);
        return object.orElse(null);
    }

    public Schedule save(Schedule schedule) {
        schedule.setId(null);
        return scheduleRepository.save(schedule);
    }

    public Schedule update(Schedule schedule) {
        Schedule scheduleDB = find(schedule.getId());

        if(scheduleDB == null || scheduleDB.getId() == null) {
            throw new IllegalArgumentException("Não foi possível encontrar registros para serem atualizados");
        }

        updateData(scheduleDB, schedule);
        return scheduleRepository.save(scheduleDB);
    }

    private void updateData(Schedule scheduleDB, Schedule schedule) {
        scheduleDB.setDateTime(schedule.getDateTime());
        scheduleDB.setProfessional(schedule.getProfessional());
    }

    public void delete(String id) {
        if (id == null) {
            throw new IllegalArgumentException("Agendamento não pode ser nulo");
        }

        Schedule scheduleDB = find(id);

        if (scheduleDB == null) {
            throw new EntityNotFoundException("Agenda não encontrada para o ID: " + scheduleDB.getId());
        }

        scheduleRepository.delete(scheduleDB);
    }
}
