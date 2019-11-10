package com.example.Gerrymender.model;

import javax.persistence.*;
import java.util.Set;

@Table(name="Cluster")
@Entity
public class Cluster {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="ClusterID")
    private long id;


    @Id
    @Column(name="stateName") //Clusters for specific state
    private String stateName;
    private Set<Precinct> precincts;
    private Set<Edge> edges;

    @Column(name="ClusterPop")
    private long population;

    @Column(name="totalPop")
    private long totalPop;
    @Column(name="Pol_Part")
    private Pol_part party;
    @Column(name="whitePop")
    private long white_pop;
    @Column(name="africanAmericanPop")
    private long africanAmerican_pop;
    @Column(name="hispanicPop")
    private long hispanic_pop;
    @Column(name="asianPop")
    private long asian_pop;
    @Column(name="nativePop")
    private long nativeAmerican_pop;


    public Set<Edge> getEdges() {
        return edges;
    }

    public void setEdges(Set<Edge> edges) {
        assert(edges!=null);
        this.edges = edges;
    }

    public Set<Precinct> getPrecincts() {
        return precincts;
    }

    public void setPrecincts(Set<Precinct> precincts) {
        assert(precincts!=null);
        this.precincts = precincts;
    }

    public String getStateName() {
        return stateName;
    }

    public void setStateName(String stateName) {
        this.stateName = stateName;
    }

    public long getPopulation() {
        return population;
    }

    public void setPopulation(long population) {
        assert(population>0);
        this.population = population;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }


    /////More functions for clusters!!!
    public void addPop(long pop){
        population+=pop;
    }

    public boolean addClusters(Set<Cluster> cl1){

        //TODO

        return false;
    }




    public long getNativeAmerican_pop() {
        return nativeAmerican_pop;
    }

    public void setNativeAmerican_pop(long nativeAmerican_pop) {
        this.nativeAmerican_pop = nativeAmerican_pop;
    }

    public long getAsian_pop() {
        return asian_pop;
    }

    public void setAsian_pop(long asian_pop) {
        this.asian_pop = asian_pop;
    }

    public long getHispanic_pop() {
        return hispanic_pop;
    }

    public void setHispanic_pop(long hispanic_pop) {
        this.hispanic_pop = hispanic_pop;
    }

    public long getAfricanAmerican_pop() {
        return africanAmerican_pop;
    }

    public void setAfricanAmerican_pop(long africanAmerican_pop) {
        this.africanAmerican_pop = africanAmerican_pop;
    }

    public long getWhite_pop() {
        return white_pop;
    }

    public void setWhite_pop(long white_pop) {
        this.white_pop = white_pop;
    }
}


