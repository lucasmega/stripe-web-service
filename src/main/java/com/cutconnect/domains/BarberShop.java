package com.cutconnect.domains;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;

import java.util.List;
import java.util.UUID;

@Entity
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler", "professionals"})
public class BarberShop {

    @Id
    private String id = UUID.randomUUID().toString();
    private String name;
    @OneToMany(mappedBy = "barberShop")
    private List<Professional> professionals;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    public List<Professional> getProfessionals() {
        return professionals;
    }
    public void setProfessionals(List<Professional> professionals) {
        this.professionals = professionals;
    }
}
