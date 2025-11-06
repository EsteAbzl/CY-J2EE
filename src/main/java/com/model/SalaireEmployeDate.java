package com.model;

import jakarta.persistence.*;

@Entity @Table(name = "Salaire_Employe_Date")

public class SalaireEmployeDate {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    @Column (length = 30)
    private Integer id;

    @Column (length = 30)
    private double salaire;

    @Column (length = 30)
    private String date;

    public Integer getId() {
        return id;
    }

    public double getSalaire() {
        return salaire;
    }

    public String getDate() {
        return date;
    }

    public void setSalaire(double salaire) {
        this.salaire = salaire;
    }

    public void setDate(Date date) {
        this.date = date;
    }

}
