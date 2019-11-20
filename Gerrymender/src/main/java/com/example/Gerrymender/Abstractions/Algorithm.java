package com.example.Gerrymender.Abstractions;


import com.example.Gerrymender.model.Pol_part;
import com.example.Gerrymender.model.Race;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class Algorithm {
    public int objectiveValue;
    public int numIterations;
    public BaseState curState;

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
}
