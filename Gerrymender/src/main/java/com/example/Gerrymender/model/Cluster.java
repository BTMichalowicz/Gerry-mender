package com.example.Gerrymender.model;

import com.example.Gerrymender.Abstractions.Area;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Set;

@Table(name="Cluster")
@Entity
public class Cluster extends Area {

    @Id
    @Column(name="stateName") //Clusters for specific states
    private String stateName;
    private Set<Precinct> precincts;
    private Set<Edge> edges;

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
}
