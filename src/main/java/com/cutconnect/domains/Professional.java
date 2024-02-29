//package com.cutconnect.domains;
//
//import com.fasterxml.jackson.annotation.JsonBackReference;
//import com.fasterxml.jackson.annotation.JsonIgnore;
//import jakarta.persistence.*;
//
//import java.util.List;
//
//@Entity
//public class Professional {
//    @Id
//    private Long idProfessional;
//
//    private String name;
//
//    private BarberShop barberShop;
//
//    private List<Schedule> appointments;
//
//    public Long getIdProfessional() {
//        return idProfessional;
//    }
//
//    public void setIdProfessional(Long idProfessional) {
//        this.idProfessional = idProfessional;
//    }
//
//    public String getName() {
//        return name;
//    }
//
//    public void setName(String name) {
//        this.name = name;
//    }
//
//    public BarberShop getBarberShop() {
//        return barberShop;
//    }
//
//    public void setBarberShop(BarberShop barberShop) {
//        this.barberShop = barberShop;
//    }
//
//    public List<Schedule> getAppointments() {
//        return appointments;
//    }
//
//    public void setAppointments(List<Schedule> appointments) {
//        this.appointments = appointments;
//    }
//}
