package com.model;

import jakarta.persistence.*;

@Entity @Table(name = "test")
public class Test {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false, length = 30)
    private String things;

    public Test() {
        this("hello !");
    }

    public Test(String things) {
        this.things = things;
    }


    public Integer getId() {return id;}

    public String getThings() {return things;}

    public void setThings(String things) {this.things = things;}

}
