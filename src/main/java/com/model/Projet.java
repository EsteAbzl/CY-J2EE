package com.model;

import jakarta.persistence.*;

@Entity @Table(name = "Projet")

public class Projet {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column (length = 30)
    private String avancement;

    public Integer getId() {
        return id;
    }

    public String getAvancement() {
        return avancement;
    }

    public void setAvancement(String avancement) {
        this.avancement = avancement;
    }


}
