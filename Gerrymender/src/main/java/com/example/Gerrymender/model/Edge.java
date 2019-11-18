package com.example.Gerrymender.model;


import javax.persistence.*;
import javax.persistence.Entity;
import javax.persistence.Table;


@Table(name="Edge")
@Entity
public class Edge {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id; //Edge id; random double, we can probably increment; make it static, perhaps, such that no id is the same, a la autoincrement

    public Edge(){}

    //private Cluster c;
//    public Edge(Cluster c){
//
//        this.c=c; //So we can access the data between two clusters
//
//    }

    private double joinability;
    //private Area neighborhood; //TODO: Define Area for our project

    //private State state;


    public boolean updateJoinability(float join){
        assert(join>=0 && join <=1);
        joinability = join;
        return true;
    }


//    public Cluster getC() {
//        return c;
//    }
//
//    public void setC(Cluster c) {
//        this.c = c;
//    }
}
