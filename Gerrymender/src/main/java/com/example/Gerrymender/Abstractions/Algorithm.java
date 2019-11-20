package com.example.Gerrymender.Abstractions;


public class Algorithm {
    public int objectiveValue;
    public int numIterations;

    public boolean combine(BaseCluster c) { //TODO
        return true; //Combines clusters
    }

    public void annealling() {
        //TODO
    }
    /*public boolean addPrecinct(State s, Precinct p) {
        return s.getPrecincts().add(p);
     }*/

    public boolean addEdge(BaseCluster c1, BaseCluster c2) {
        //return c1.getEdges().add(new Edge());
        return true;

    }
}
