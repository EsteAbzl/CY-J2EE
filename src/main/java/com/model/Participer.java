package com.model;

import jakarta.persistence.*;

@Entity
@Table(name = "Participer")
public class Participer {

    @EmbeddedId
    private ParticiperId id;

    @ManyToOne
    @MapsId("idEmploye")
    @JoinColumn(name = "idEmploye")
    private Employe employe;

    @ManyToOne
    @MapsId("idProjet")
    @JoinColumn(name = "idProjet")
    private Projet projet;

    public ParticiperId getId() {
        return id;
    }

    public Employe getEmploye() {
        return employe;
    }

    public void setEmploye(Employe employe) {
        this.employe = employe;
    }

    public Projet getProjet() {
        return projet;
    }

    public void setProjet(Projet projet) {
        this.projet = projet;
    }

}
