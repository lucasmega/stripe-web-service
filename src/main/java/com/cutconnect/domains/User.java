package com.cutconnect.domains;

import jakarta.persistence.Id;
import jakarta.persistence.Entity;

import java.io.Serial;
import java.io.Serializable;

@Entity(name = "USUARIO")
public class User implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    public User() { }

    public User(String id, String email) {
        this.id = id;
        this.email = email;
    }

    public User(String id, String email, String accountId) {
        this.id = id;
        this.email = email;
        this.accountId = accountId;
    }

    public User(String id, String email,String accountId, String barbershopId) {
        this.id = id;
        this.email = email;
        this.accountId = accountId;
        this.barbershopId = barbershopId;
    }

    @Id
    private String id;
    private String email;
    private String barbershopId;
    private String accountId;

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

    public String getBarbershopId() {
        return barbershopId;
    }

    public void setBarbershopId(String barbershopId) {
        this.barbershopId = barbershopId;
    }

    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }
}
