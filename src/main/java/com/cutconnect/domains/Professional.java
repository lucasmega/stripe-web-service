package com.cutconnect.domains;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Professional implements Serializable {
    private static final long serialVersionUID = 1L;
    public Professional() { }
    public Professional(String id, String name, String position) {
        this.id = id;
        this.name = name;
        this.position = position;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    private String name;

    @JsonIgnore
    @ManyToOne
    @JoinColumn()
    private Branch branch;

    @OneToMany(mappedBy = "professional")
    private List<Schedule> schedules = new ArrayList<>();

    public String position;

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

    public Branch getBranch() {
        return branch;
    }
    public void setBranch(Branch branch) {
        this.branch = branch;
    }
    public List<Schedule> getSchedules() {
        return schedules;
    }

    public void setSchedules(List<Schedule> schedules) {
        this.schedules = schedules;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }
}
