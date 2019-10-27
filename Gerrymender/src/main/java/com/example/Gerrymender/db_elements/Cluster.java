package com.example.Gerrymender.db_elements;

import com.example.Gerrymender.Abstractions.Area;
import com.example.Gerrymender.db_elements.Edge;
import com.example.Gerrymender.db_elements.Precinct;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.Set;

@Table(name="Cluster")
@Entity
public class Cluster extends Area {

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
}
