package com.example.Gerrymender.Abstractions;

import com.example.Gerrymender.model.Pol_part;
import com.example.Gerrymender.model.Precinct;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class BaseCluster {
    private String ID;
    private BaseState state;
    private int population;
    private int internalEdges = 0;
    private int externalEdges = 0;
    private Map<Pol_part, Integer> votes;
    private HashMap<String, BasePrecinct> precincts;
    private HashSet<BaseCluster> edges;
    private int[] racePops;
    public BaseCluster(String ID, BaseState state, int population, int[] racePops) {
        this.ID = ID;
        this.state = state;
        this.population = population;
        this.precincts = new HashMap<>();
        this.votes = new HashMap<>();
        this.edges = new HashSet<>();
        this.racePops = racePops;
    }

    public String getID() {
        return ID;
    }

    public Map<Pol_part, Integer> getVotes() {
        return votes;
    }

    public BaseState getState() {
        return state;
    }

    public int getPopulation() {
        return population;
    }

    public int getInternalEdges() {
        return internalEdges;
    }

    public int getExternalEdges() {
        return externalEdges;
    }

    public Map<String, BasePrecinct> getPrecincts() {
        return precincts == null ? new HashMap<>() :  precincts;
    }

    public Set<BaseCluster> getEdges() { return edges;    }

    public void setEdges(HashSet<BaseCluster> edges) { this.edges = edges;    }

    public void combine(BaseCluster c) {
        precincts.putAll(c.getPrecincts());
        for(String s : c.getPrecincts().keySet()) {
            c.getPrecincts().get(s).setClusterId(Integer.parseInt(ID));
        }
        for(int i = 0; i < racePops.length; i++) {
            racePops[i] += c.getRacePops()[i];
        }
        edges.addAll(c.getEdges());
        population += c.getPopulation();
    }

    public void addPrecinct(BasePrecinct p) {
        precincts.put(p.getID(), p);
    }

    public void addEdge(BaseCluster c) {
        edges.add(c);
    }

    public int[] getRacePops() { return racePops; }
}