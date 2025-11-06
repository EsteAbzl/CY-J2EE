package com.model;

import jakarta.persistence.*;

@Entity @Table(name = "Employe")
public class Employe {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column (length = 30)
    private String nom;

    @Column (length = 30)
    private String prenom;

    @Column (length = 30)
    private String email;

    @Column (length = 30)
    private String mdp;

    @Column (length = 30)
    private String poste;

    @Column (length = 30)
    private String grade;

    @Column (length = 30)
    private Boolean estArchive;

    @Column (length = 30)
    private Integer idDepartement;

    public Integer getId(){
        return id;
    }

    public String getNom(){
        return nom;
    }

    public String getPrenom(){
        return prenom;
    }

    public String getEmail(){
        return email;
    }

    public String getMdp(){
        return mdp;
    }

    public String getPoste(){
        return poste;
    }

    public String getGrade(){
        return grade;
    }

    public Boolean getEstArchive(){
        return estArchive;
    }

    public Integer getIdDepartement(){
        return idDepartement;
    }

    public void setNom(String nom){
        this.nom = nom;
    }

    public void setPrenom(String prenom){
        this.prenom = prenom;
    }

    public void setEmail(String email){
        this.email = email;
    }

    public void setMdp(String mdp){
        this.mdp = mdp;
    }

    public void setPoste(String poste){
        this.poste = poste;
    }

    public void setGrade(String grade){
        this.grade = grade;
    }

    public void setEstArchive(Boolean estArchive){
        this.estArchive = estArchive;
    }

    public void setIdDepartement(Integer idDepartement){
        this.idDepartement = idDepartement;
    }

}
