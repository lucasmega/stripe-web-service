package com.cutconnect.domains.form;

import java.time.LocalDate;

public class ScheduleFromProfissional {

    private String id;

    private LocalDate date;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }
}
