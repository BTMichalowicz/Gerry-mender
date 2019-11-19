package com.example.Gerrymender.model;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Set;

@Table(name="Cluster")
@Entity
public class  Cluster implements Serializable {

    private static final long SerializeID = 222L; //or some other ID


    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column
    private long clusterid;


    @Id
    @Column
    private String statename;
//    private ArrayList<Precinct> precincts;
//    private ArrayList<Edge> edges;

    @Column
    private long population;

    @Column
    private long totalpop;
    @Column
    private String party;
    @Column
    private long whitepop;
    @Column
    private long africanamericanpop;
    @Column
    private long hispanicpop;
    @Column
    private long asianpop;
    @Column
    private long nativeamericanpop;


//    public ArrayList<Edge> getEdges() {
//        return edges;
//    }
//
//    public void setEdges(ArrayList<Edge> edges) {
//        assert(edges!=null);
//        this.edges = edges;
//    }
//
//    public ArrayList<Precinct> getPrecincts() {
//        return precincts;
//    }
//
//    public void setPrecincts(ArrayList<Precinct> precincts) {
//        assert(precincts!=null);
//        this.precincts = precincts;
//    }

    public String getStateName() {
        return statename;
    }

    public void setStateName(String stateName) {
        this.statename = stateName;
    }

    public long getPopulation() {
        return population;
    }

    public void setPopulation(long population) {
        assert(population>0);
        this.population = population;
    }

    public long getId() {
        return clusterid;
    }

    public void setId(long id) {
        this.clusterid = id;
    }


    /////More functions for clusters!!!
    public void addPop(long pop){
        population+=pop;
    }

    public boolean addClusters(ArrayList<Cluster> cl1){

        //TODO

        return false;
    }




    public long getNativeAmerican_pop() {
        return nativeamericanpop;
    }

    public void setNativeAmerican_pop(long nativeAmerican_pop) {
        this.nativeamericanpop = nativeAmerican_pop;
    }

    public long getAsian_pop() {
        return asianpop;
    }

    public void setAsian_pop(long asian_pop) {
        this.asianpop = asian_pop;
    }

    public long getHispanic_pop() {
        return hispanicpop;
    }

    public void setHispanic_pop(long hispanic_pop) {
        this.hispanicpop = hispanic_pop;
    }

    public long getAfricanAmerican_pop() {
        return africanamericanpop;
    }

    public void setAfricanAmerican_pop(long africanAmerican_pop) {
        this.africanamericanpop = africanAmerican_pop;
    }

    public long getWhite_pop() {
        return whitepop;
    }

    public void setWhite_pop(long white_pop) {
        this.whitepop = white_pop;
    }
}


