package com.example.Gerrymender.Abstractions;

import com.example.Gerrymender.db_elements.Cluster;

public class Algorithm {
    /*objectiveValue -> calculating the objective function for each iteration
        numIterations -> For simulated annealing
     */
    public int objectiveValue;
    public int numIterations;

    public boolean combine(Cluster c){ //TODO
        return true; //Combines clusters
    }

    public boolean addEdge(Cluster c){
        return true; //TODO
    }
}
