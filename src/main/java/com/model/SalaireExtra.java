package com.model;

import jakarta.persistence.*;

@Entity @Table(name = "Salaire_extra")


public class SalaireExtra {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column (length = 30)
    private String date;

    @Column  (length = 30)
    private double montant;

    public Integer  getId() {
        return id;
    }

    public String getDate() {
        return date;
    }

    public double getMontant() {
        return montant;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setMontant(double montant) {
        this.montant = montant;
    }

}
