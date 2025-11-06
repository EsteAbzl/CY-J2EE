package com.model;

import jakarta.persistence.*;

@Entity @Table(name = "Departement")

public class Departement {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column (length = 30)
    private String nom;

    public Integer getId(){
        return id;
    }

    public String getNom(){
        return nom;
    }

    public void setNom(String nom){
        this.nom = nom;
    }

}
