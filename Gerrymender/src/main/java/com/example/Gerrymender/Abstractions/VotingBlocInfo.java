package com.example.Gerrymender.Abstractions;

import com.example.Gerrymender.model.Pol_part;

public class VotingBlocInfo {
    public String precinctId;
    public Pol_part party;
    public int totalVotes;
    public int partyVotes;
    public VotingBlocInfo(String precinctID, Pol_part party, int totalVotes, int partyVotes) {
        this.party = party;
        this.partyVotes = partyVotes;
        this.precinctId = precinctID;
        this.totalVotes = totalVotes;
    }
}
