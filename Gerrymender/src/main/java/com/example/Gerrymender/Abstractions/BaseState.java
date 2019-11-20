package com.example.Gerrymender.Abstractions;

import java.util.*;

public class BaseState {
    private String name;
    private Map<String, BaseDistrict> districts;
    private Map<String, BasePrecinct> precincts;
    private Map<String, BaseCluster> clusters;
    private int population;

    public String getName() {
        return name;
    }

    public Map<String, BasePrecinct> getPrecincts() {
        return precincts;
    }

    public Set<BaseDistrict> getDistricts() {
        return districts == null ? new HashSet<>() : (Set<BaseDistrict>) districts.values();
    }

    public int getPopulation() {
        return population;
    }

    public Map<String, BaseCluster> getClusters() { return clusters;    }

    public void setClusters(Map<String, BaseCluster> clusters) { this.clusters = clusters;    }
}