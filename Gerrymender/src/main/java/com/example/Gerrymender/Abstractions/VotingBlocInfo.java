package com.example.Gerrymender.Abstractions;

import com.example.Gerrymender.model.Pol_part;
import com.example.Gerrymender.model.Race;

public class VotingBlocInfo {
    public String precinctId;
    public Pol_part party;
    public int totalVotes;
    public int partyVotes;
    public Race majorityRace;
    public VotingBlocInfo(String precinctID, Pol_part party, int totalVotes, int partyVotes, Race majorityRace) {
        this.party = party;
        this.partyVotes = partyVotes;
        this.precinctId = precinctID;
        this.totalVotes = totalVotes;
        this.majorityRace = majorityRace;
    }
}
