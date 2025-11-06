package com.model;

import jakarta.persistence.*;

@Entity @Table(name = "Role")

public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column (length = 30)
    private String libelle;

    @Column (length = 30)
    private Integer idEmploye;

    public Integer getId() {
        return id;
    }

    public String getLibelle() {
        return libelle;
    }

    public Integer getIdEmploye() {
        return idEmploye;
    }

    public void setLibelle(String libelle) {
        this.libelle = libelle;
    }

    public void setIdEmploye(Integer idEmploye) {
        this.idEmploye = idEmploye;
    }
}
