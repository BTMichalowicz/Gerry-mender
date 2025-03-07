package com.example.Gerrymender.Abstractions;

import com.example.Gerrymender.model.Pol_part;
import com.example.Gerrymender.model.Vote;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * The method of holding votes for any set of elections
 * 2016 Presidential
 * 2018 General/Congressional
 * 2016 General/Congressional
 */
public class Votes {
    private int year;
    private boolean isPresidential; //Boolean to determine if we are using
    private Pol_part party; // winning party
    private long[] votes; //Indexed based on party
    private long totalVotes;

    public Votes(Vote v) {
        this.year = Integer.parseInt(v.getElectionyear());
        this.isPresidential = v.getElectionname().equals("PRESIDENT");
        this.votes = new long[]{ v.getNumdemocrat(), v.getNumrepub(), v.getTotalvote() - v.getNumrepub() - v.getNumdemocrat()};
        this.totalVotes = v.getTotalvote();
        this.party = v.getWinner().charAt(0) == 'R' ? Pol_part.REPUBLICAN : v.getWinner().charAt(0) == 'D' ? Pol_part.DEMOCRAT : Pol_part.OTHER;
    }

    public int getYear() {
        return year;
    }
    public void setYear(int year) {
        this.year = year;
    }

    public boolean isPresidential() {
        return isPresidential;
    }
    public void setPresGen(boolean presGen) {
        this.isPresidential = presGen;
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

    public void combine(Votes v) {
        for(int i = 0; i < votes.length; i++) {
            votes[i] += v.getVotes()[i];
        }
    }
}
