package com.example.Gerrymender.model;

import javax.persistence.*;

import static org.junit.jupiter.api.Assertions.assertNotEquals;

@Table(name="Votes")
@Entity
public class Vote {
    @Column
    private int totalvotes;

    @Column
    private int numrepub;

    @Column
    private int numdemocrat;

    @Column
    private String winner;

    @Column
    private String statename;

    @EmbeddedId
    private VoteId voteid;


    public String getElectionname() { return voteid.electionname; }
    public void setElectionname(String electionname) { this.voteid.electionname = electionname; }

    public void setElectionyear(String electionyear) { this.voteid.electionyear = electionyear; }
    public String getElectionyear() { return voteid.electionyear; }

    public String getStateName(){return statename;}
    public void setStateName(String districtID ){this.statename=districtID;}

    public String getNameID() {
        return voteid.precinctid;
    }
    public void setNameID(String nameID) { this.voteid.precinctid = nameID; }

    public String getWinner() {
        return winner;
    }

    public void setWinner(String winner) {
        this.winner = winner;
    }

    public int getTotalvote() {
        return totalvotes;
    }

    public void setTotalvote(int totalvote) {
        this.totalvotes = totalvote;
    }

    public int getNumdemocrat() {
        return numdemocrat;
    }

    public void setNumdemocrat(int numdemocrat) {
        this.numdemocrat = numdemocrat;
    }

    public int getNumrepub() {
        return numrepub;
    }

    public void setNumrepub(int numrepub) {
        this.numrepub = numrepub;
    }
}
