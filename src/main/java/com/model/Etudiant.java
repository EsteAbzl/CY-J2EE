package com.model;

import jakarta.persistence.*;

@Entity @Table(name = "etudiant")
public class Etudiant {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false, length = 50)
    private String nom;

    @Column(nullable = false, length = 50)
    private String prenom;

    @Column
    private int age;

    public Etudiant() {
        this("inconnu", "inconnu", 0);
    }

    public Etudiant(String nom, String prenom, int age) {
        this.nom = nom;
        this.prenom = prenom;
        this.age = age;
    }

    public String getNom() {return nom;}

    public void setNom(String nom) {this.nom = nom;}

    public String getPrenom() {return prenom;}

    public void setPrenom(String prenom) {this.prenom = prenom;}

    public Integer getId() {return id;}

    public int getAge() {return age;}

    public void setAge(int age) {this.age = age;}

}
