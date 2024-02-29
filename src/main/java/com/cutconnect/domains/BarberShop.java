package com.cutconnect.domains;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;

import java.util.List;
import java.util.ArrayList;
import java.io.Serializable;

@Entity
public class BarberShop implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    private String name;

    @JsonIgnoreProperties
    @OneToMany(mappedBy = "barberShop")
    private List<Professional> professionails = new ArrayList<>();

    @OneToMany(mappedBy = "barberShop")
    private List<Branch> branches = new ArrayList<>();

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

    public List<Branch> getBranches() {
        return branches;
    }

    public void setBranches(List<Branch> branches) {
        this.branches = branches;
    }

    public List<Professional> getProfessionails() {
        return professionails;
    }

    public void setProfessionails(List<Professional> professionails) {
        this.professionails = professionails;
    }
}
