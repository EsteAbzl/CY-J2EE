package com.model;

import jakarta.persistence.*;

@Entity
@Table(name = "Recevoir")
public class Recevoir {

    @EmbeddedId
    private RecevoirId id;

    @ManyToOne
    @MapsId("idEmploye")
    @JoinColumn(name = "idEmploye")
    private Employe employe;

    @ManyToOne
    @MapsId("idSalaireEmployeDate")
    @JoinColumn(name = "idSalaire_Employe_Date")
    private SalaireEmployeDate salaireEmployeDate;

    public RecevoirId getId() {
        return id;
    }

    public Employe getEmploye() {
        return employe;
    }

    public void setEmploye(Employe employe) {
        this.employe = employe;
    }

    public SalaireEmployeDate getSalaireEmployeDate() {
        return salaireEmployeDate;
    }
    public void setSalaireEmployeDate(SalaireEmployeDate salaireEmployeDate) {
        this.salaireEmployeDate = salaireEmployeDate;
    }
}

