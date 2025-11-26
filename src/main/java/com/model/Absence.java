package com.model;

import java.sql.Date;
import jakarta.persistence.*;

@Entity @Table(name = "absences")

public class Absence {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(length = 30)
    private int employeeId;

    @Column(length = 30)
    private Date date;

    @Column(length = 30)
    private String type;   // CONGE, MALADIE, NON_PAYE

    @Column(length = 30)
    private int hours;

    // --- Getters & Setters ---
    public Integer getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    public int getEmployeeId() {
        return employeeId;
    }
    public void setEmployeeId(int employeeId) {
        this.employeeId = employeeId;
    }

    public Date getDate() {
        return date;
    }
    public void setDate(Date date) {
        this.date = date;
    }

    public String getType() {
        return type;
    }
    public void setType(String type) {
        this.type = type;
    }

    public int getHours() {
        return hours;
    }
    public void setHours(int hours) {
        this.hours = hours;
    }
}
