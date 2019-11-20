package com.example.Gerrymender.Abstractions;

import com.example.Gerrymender.model.Pol_part;
import com.example.Gerrymender.model.Race;

import java.util.*;

public class BasePrecinct {
    private String ID;
    private int population;
    private Map<Pol_part, Integer> votes;
    private VotingBloc bloc;
    private Race majorityRace;
    private int majorityRacePop;

    public VotingBloc getBloc() {
        return bloc;
    }

    public void setBloc(VotingBloc bloc) {
        this.bloc = bloc;
    }

    public int getPopulation() {
        return population;
    }

    public Map<Pol_part, Integer> getVotes() {
        return votes;
    }

    public void setVotes(Map<Pol_part, Integer> votes) {
        this.votes = votes;
    }

    public String getID() {
        return ID;
    }

    public int getMajorityRacePop() { return majorityRacePop; }

    public Race getMajorityRace() { return majorityRace;    }

    public void setMajorityRace(Race majorityRace) { this.majorityRace = majorityRace;    }

    public void setMajorityRacePop(int majorityRacePop) { this.majorityRacePop = majorityRacePop;    }
}