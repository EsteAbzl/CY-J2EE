package com.model;

import jakarta.persistence.*;

@Entity @Table(name = "Projet")

public class Projet {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column (length = 30)
    private String avancement;

    @Column(length = 30)
    private String nom;

    public Integer getId() {
        return id;
    }

    public String getAvancement() {
        return avancement;
    }

    public String getNom(){
        return nom;
    }

    public void setAvancement(String avancement) {
        this.avancement = avancement;
    }

    public void setNom(String nom){
        this.nom = nom;
    }

    public Projet(){

    }
    public Projet(Integer id, String nom, String avancement){
        this.id = id;
        this.nom = nom;
        this.avancement = avancement;
    }


}
