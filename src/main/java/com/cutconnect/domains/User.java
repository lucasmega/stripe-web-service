package com.cutconnect.domains;

import jakarta.persistence.Id;
import jakarta.persistence.Entity;

import java.io.Serializable;

@Entity(name = "USUARIO")
public class User implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    private String id;
    private String email;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
