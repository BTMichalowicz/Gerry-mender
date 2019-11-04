package com.example.Gerrymender.Abstractions;



import com.example.Gerrymender.model.Pol_part;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * The method of holding votes for any set of elections
 * 2016 Presidential
 * 2018 General/Congressional
 * 2016 General/Congressional
 */
public class Votes {
    private int year;

    private boolean presGen; //Boolean to determine if we are using
    private Pol_part party;
    private long[] votes; //Indexed based on race
    private long totalVotes;

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public boolean isPresGen() {
        return presGen;
    }

    public void setPresGen(boolean presGen) {
        this.presGen = presGen;
    }

    public long[] getVotes() {
        return votes;
    }

    public void setVotes(long[] votes) {
        assert(votes!=null);
        assert(votes.length==5);
        long i =0;
        for(long voteset : votes){
            i+=voteset;
        }

        assertEquals(i, totalVotes);
        this.votes = votes;
    }

    public Pol_part getParty() {
        return party;
    }

    public void setParty(Pol_part party) {
        this.party = party;
    }

    public long getTotalVotes() {
        return totalVotes;
    }

    public void setTotalVotes(long totalVotes) {
        this.totalVotes = totalVotes;
    }
}
