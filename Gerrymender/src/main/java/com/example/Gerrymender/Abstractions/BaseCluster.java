package com.example.Gerrymender.Abstractions;

import com.example.Gerrymender.model.District;
import com.example.Gerrymender.model.Pol_part;
import com.example.Gerrymender.model.Precinct;
import com.example.Gerrymender.model.Race;
import reactor.util.function.Tuple2;
import reactor.util.function.Tuples;

import java.lang.Math;
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
    private String countyName;

    public BaseCluster(String ID, BaseState state, int population, int[] racePops, String countyName) {
        this.ID = ID;
        this.state = state;
        this.population = population;
        this.precincts = new HashMap<>();
        this.votes = new HashMap<>();
        this.edges = new HashSet<>();
        this.racePops = racePops;
        this.countyName = countyName;
    }

    public String getCountyName() { return countyName; }

    public void setCountyName(String countyName) { this.countyName = countyName; }

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
            if(countyName != null && c.getPrecincts().get(s).getCountyName() != countyName) {
                countyName = null;
            }
        }
        for(int i = 0; i < racePops.length; i++) {
            racePops[i] += c.getRacePops()[i];
        }
        edges.addAll(c.getEdges());
        population += c.getPopulation();
        ID = Integer.parseInt(ID) < Integer.parseInt(c.getID()) ? ID : c.getID();
    }

    public void addPrecinct(BasePrecinct p) {
        precincts.put(p.getID(), p);
    }

    public void addEdge(BaseCluster c) {
        edges.add(c);
    }

    public int[] getRacePops() { return racePops; }

    public Tuple2<Double, Double> joinability(BaseCluster c, double minPopPerc, double maxPopPerc, int avgPop, double avgPopEpsilon, Race races[], boolean lastIteration, BaseState state) {
        double maxRacialJoinability = 0;
        for(Race r : races) {
            double joinability;
            double demPopPerc = (double) (racePops[r.ordinal()] + c.racePops[r.ordinal()]) / (double) (population + c.getPopulation());
            if(demPopPerc < minPopPerc) {
                joinability = 2 *Math.exp((demPopPerc - 2*minPopPerc) / minPopPerc);
            }
            else if(demPopPerc < maxPopPerc) {
                joinability = 1;
            }
            else {
                joinability = 2 * Math.exp(((1 - demPopPerc) - 2*maxPopPerc)/  maxPopPerc);
            }
            if(joinability > maxRacialJoinability) {
                maxRacialJoinability = joinability;
            }

        }

        double otherJoinability = 0;
        if(lastIteration) {
            if (population + c.getPopulation() <= avgPop + avgPopEpsilon && population + c.getPopulation() >= avgPop - avgPopEpsilon) {
                otherJoinability++;
            }
            return Tuples.of(maxRacialJoinability, otherJoinability);
        }
        if(countyName != null && c.getCountyName() != null && countyName.equalsIgnoreCase(c.countyName)) {
            otherJoinability++;
        }
        if(population + c.getPopulation() <= avgPop + avgPopEpsilon) {
            otherJoinability++;
        }
        BaseDistrict temporaryDistrict = new BaseDistrict("temp", state);
        for(String s : precincts.keySet()) {
            temporaryDistrict.addPrecinct(precincts.get(s));
        }
        for(String s : c.getPrecincts().keySet()) {
            temporaryDistrict.addPrecinct(c.getPrecincts().get(s));
        }
        otherJoinability += temporaryDistrict.getInternalEdges() / (temporaryDistrict.getInternalEdges() + temporaryDistrict.getExternalEdges());
        return Tuples.of(maxRacialJoinability, otherJoinability);
    }

    public static boolean maxJoinability(Tuple2<Double, Double> max, Tuple2<Double, Double> compare, boolean lastIter) {
        if(lastIter) {
            if (max.getT2() < compare.getT2()) {
                return true;
            } else if (max.getT2() == compare.getT2()) {
                return max.getT1() < compare.getT1() ? true : false;
            }
            return false;
        }
        else {
            if (max.getT1() < compare.getT1()) {
                return true;
            } else if (max.getT1() == compare.getT1()) {
                return max.getT2() < compare.getT2() ? true : false;
            }
            return false;
        }
    }
}