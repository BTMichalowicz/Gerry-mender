package com.example.Gerrymender.Abstractions;

import com.example.Gerrymender.model.Pol_part;
import com.example.Gerrymender.model.Race;

import java.util.*;

public class BasePrecinct {
    private String ID;
    private int population;
    private Map<Pol_part, Integer> votes;
    private Race votingBlockRace;

    public Race getVotingBlockRace() {
        return votingBlockRace;
    }

    public void setVotingBlockRace(Race votingBlockRace) {
        this.votingBlockRace = votingBlockRace;
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
}