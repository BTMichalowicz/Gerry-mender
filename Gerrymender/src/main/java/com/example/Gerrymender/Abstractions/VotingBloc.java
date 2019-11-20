package com.example.Gerrymender.Abstractions;

import com.example.Gerrymender.model.Pol_part;
import com.example.Gerrymender.model.Race;

public class VotingBloc {
    private Race r;
    private int votes;
    private Pol_part party;

    public VotingBloc() {}

    public VotingBloc(Race r, int votes, Pol_part party) {
        this.r = r;
        this.votes = votes;
        this.party = party;
    }

    public int getVotes() { return votes; }

    public void setVotes(int votes) { this.votes = votes; }

    public Pol_part getParty() { return party; }

    public void setParty(Pol_part party) { this.party = party; }

    public Race getRace() { return r; }

    public void setRace(Race r){ this.r = r; }
}
