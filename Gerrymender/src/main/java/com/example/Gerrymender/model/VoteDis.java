package com.example.Gerrymender.model;

import javax.persistence.*;

@Table(name="DistVotes")
@Entity
public class VoteDis {
    @EmbeddedId VoteDisId voteid;
    @Column
    private int numrepub;
    @Column
    private int numdemocrat;
    @Column
    private int totalvotes;
    @Column
    private String winner;


    public int getNumrepub() {
        return numrepub;
    }

    public int getNumdemocrat() {
        return numdemocrat;
    }
}
