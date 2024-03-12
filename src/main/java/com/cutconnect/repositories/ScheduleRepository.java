package com.cutconnect.repositories;

import java.time.LocalDate;
import java.util.List;
import java.time.LocalDateTime;
import org.springframework.data.jpa.repository.JpaRepository;

import com.cutconnect.domains.Schedule;
import com.cutconnect.domains.Professional;

public interface ScheduleRepository extends JpaRepository<Schedule, String> {
    List<Schedule> findScheduleByProfessionalIdAndDate(String professionalId, LocalDate date);
    List<Schedule> findScheduleByDate(LocalDate date);

}
