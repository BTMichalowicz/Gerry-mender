package com.example.Gerrymender.Abstractions;


import com.example.Gerrymender.model.Pol_part;
import com.example.Gerrymender.model.Precinct;
import com.example.Gerrymender.model.Race;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class Algorithm {
    public int objectiveValue;
    public int numIterations;
    public BaseState curState;


    //  Combines c1 with c2 and removes c2 from the hashmap of clusters
    private void combine(BaseCluster c1, BaseCluster c2) {
        c1.combine(c2);
        for(BaseCluster c : c2.getEdges())
        {
            HashSet<BaseCluster> edge = c.getEdges();
            edge.remove(c2);
            edge.add(c1);
        }
        Map<String, BaseCluster> map = curState.getClusters();
        map.entrySet().removeIf(entry -> c2.equals(entry.getValue()));
    }

    public void annealling() {
        //TODO
    }
    /*public boolean addPrecinct(State s, Precinct p) {
        return s.getPrecincts().add(p);
     }*/

    private HashMap<String, BaseCluster> initializeClusters(Set<BasePrecinct> precincts)
    {
        HashMap<String, BaseCluster> clusters = new HashMap<>();
        int count = 0;
        for(BasePrecinct p : precincts) {
            BaseCluster c = new BaseCluster("" + count, curState, p.getPopulation());
            p.setClusterId(count);
            c.addPrecinct(p);
            clusters.put("" + count, c);
        }
        for(Map.Entry<String, BaseCluster> clust : clusters.entrySet())
        {
            BaseCluster c = clust.getValue();
            Map<String, BasePrecinct> prec = c.getPrecincts();
            for(Map.Entry<String, BasePrecinct> p : prec.entrySet())
            {
                for(BasePrecinct precinct : p.getValue().getEdges())
                {
                    c.addEdge(clusters.get("" + precinct.getClusterId()));
                }
            }
        }
        return clusters;
    }

    public Set<BasePrecinct> phase0(double popThreshold, double voteThreshold)
    {
        if(curState == null)
        {
            return null;
        }

        Map<String, BasePrecinct> precints = curState.getPrecincts();
        Set<BasePrecinct> ret = new HashSet<BasePrecinct>();
        for(Map.Entry<String, BasePrecinct> entry : precints.entrySet())
        {
            BasePrecinct p = entry.getValue();
            Race r = p.getMajorityRace();
            double perc = (double) p.getMajorityRacePop() / (double) p.getPopulation();
            if(perc >= popThreshold)
            {
                int max = 0;
                Pol_part maxParty = null;
                for(Map.Entry<Pol_part, Integer> voteEntry  : p.getVotes().entrySet())
                {
                    if(voteEntry.getValue() > max)
                    {
                        max = voteEntry.getValue();
                        maxParty = voteEntry.getKey();
                    }
                }

                double votePerc = (double) max / (double) p.getPopulation();
                if(votePerc >= voteThreshold)
                {
                    p.setBloc(new VotingBloc(r, max, maxParty));
                    ret.add(p);
                }
            }
        }
        return ret;
    }

    public Map<String, String> phase1(int numDistricts, boolean runFull){
        if(runFull) {
            // Run the entire phase until the end
        }
        else {
            // Run a single step
        }
        return null; // placeholder
    }
}
