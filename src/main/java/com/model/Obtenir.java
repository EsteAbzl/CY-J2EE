package com.model;

import jakarta.persistence.*;

@Entity
@Table(name = "Obtenir")
public class Obtenir {

    @EmbeddedId
    private ObtenirId id;

    @ManyToOne
    @MapsId("idEmploye") 
    @JoinColumn(name = "idEmploye")
    private Employe employe;

    @ManyToOne
    @MapsId("idSalaireExtra")
    @JoinColumn(name = "idSalaire_extra")
    private SalaireExtra salaireExtra;

    public ObtenirId getId() {
        return id;
    }

    public Employe getEmploye() {
        return employe;
    }

    public SalaireExtra getSalaireExtra() {
        return salaireExtra;
    }

    public void setSalaireExtra(SalaireExtra salaireExtra) {
        this.salaireExtra = salaireExtra;
    }
}

