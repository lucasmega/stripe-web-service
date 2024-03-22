package com.cutconnect.services;

import com.cutconnect.domains.form.ScheduleFromBarbershop;
import com.cutconnect.exceptions.BusinessException;
import com.cutconnect.exceptions.InternalServerError;
import jakarta.persistence.EntityNotFoundException;

import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.*;
import java.time.LocalTime;
import java.util.stream.Collectors;

import com.cutconnect.utils.Util;
import com.cutconnect.domains.User;
import com.cutconnect.domains.Schedule;
import com.cutconnect.domains.Professional;
import com.cutconnect.repositories.UserRepository;
import com.cutconnect.repositories.ScheduleRepository;
import com.cutconnect.repositories.ProfessionalRepository;
import com.cutconnect.domains.form.ScheduleFromProfissional;

@Service
public class ScheduleService extends Util {
    private final UserRepository userRepository;
    private final ScheduleRepository scheduleRepository;
    private final ProfessionalRepository professionalRepository;

    @Autowired
    public ScheduleService(ScheduleRepository scheduleRepository, ProfessionalRepository professionalRepository, UserRepository userRepository) {
        this.scheduleRepository = scheduleRepository;
        this.professionalRepository = professionalRepository;
        this.userRepository = userRepository;
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
        scheduleDB.setDate(schedule.getDate());
        scheduleDB.setTime(schedule.getTime());
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


    public List<LocalTime> findHourByProfessional(ScheduleFromProfissional object) {
        List<Schedule> schedulesDB = scheduleRepository.findScheduleByProfessionalIdAndDate(object.getId(), object.getDate());

        List<LocalTime> possibleTimes = new ArrayList<>();
        LocalTime startTime = LocalTime.of(9, 0);
        LocalTime endTime = LocalTime.of(18, 0);

        LocalTime currentTime = startTime;
        while (!currentTime.isAfter(endTime)) {
            possibleTimes.add(currentTime);
            currentTime = currentTime.plusMinutes(60);
        }

        List<String> existingTimesAsString = schedulesDB.stream()
                .map(schedule -> schedule.getTime().toString())
                .collect(Collectors.toList());

        possibleTimes.removeIf(time -> existingTimesAsString.contains(time.toString()));

        return possibleTimes;
    }

    public List<LocalTime> findHourByDateFromBarbershop(ScheduleFromBarbershop object) {

        try {

            if(!isEmailValid(object.getEmail())) {
                throw new BusinessException("Email inválido.");
            }

            if (!isDateValid(object.getDate())) {
                throw new BusinessException("Data inválida.");
            }

            User user = hasFavoriteBarbershop(object.getEmail());

            if (user == null || user.getBarbershopId() == null) {
                throw new BusinessException("Para verificar os horários disponíveis é necessário favoritar uma barbearia.");
            }

            List<Schedule> schedulesDB = scheduleRepository.findScheduleByDate(convertStringToLocalDate(object.getDate()));
            List<Professional> professionalDB = professionalRepository.findProfessionalsByBranchId(user.getBarbershopId());

            List<LocalTime> possibleTimes = generatePossibleTimes();

            Iterator<LocalTime> iterator = possibleTimes.iterator();
            while (iterator.hasNext()) {
                LocalTime currentTime = iterator.next();

                boolean allBarbersOccupied = professionalDB.stream()
                        .allMatch(professional -> isOccupied(professional, currentTime, schedulesDB));

                if (allBarbersOccupied) {
                    iterator.remove();
                }
            }

            return possibleTimes;

        } catch (BusinessException e) {
            throw new BusinessException(e.getMessage());

        } catch (Exception e) {
            loggerException("120320241257", e.getMessage());
            throw new InternalServerError("Não foi possível buscar os horários disponíveis");
        }
    }

    private boolean isOccupied(Professional professional, LocalTime currentTime, List<Schedule> schedules) {
        return schedules.stream()
                .anyMatch(schedule -> schedule.getProfessional().equals(professional) && schedule.getTime().equals(currentTime));
    }

    private List<LocalTime> generatePossibleTimes() {
        List<LocalTime> possibleTimes = new ArrayList<>();
        LocalTime startTime = LocalTime.of(9, 0);
        LocalTime endTime = LocalTime.of(18, 0);

        LocalTime currentTime = startTime;
        while (!currentTime.isAfter(endTime)) {
            possibleTimes.add(currentTime);
            currentTime = currentTime.plusMinutes(60);
        }

        return possibleTimes;
    }

    private User hasFavoriteBarbershop(String email) {
        return userRepository.findByEmail(email);
    }

}
