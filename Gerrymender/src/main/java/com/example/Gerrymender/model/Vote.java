package com.example.Gerrymender.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import static org.junit.jupiter.api.Assertions.assertNotEquals;

@Table(name="VoteRecord")
@Entity
public class Vote {
    @Id //ID Annotation for the subclasses
    @Column
    private String recordID;

    @Column
    private int totalvote;

    @Column
    private int repvote;

    @Column
    private int demvote;

    @Column
    private String winner;

    public String getWinner() {
        return winner;
    }

    public void setWinner(String winner) {
        this.winner = winner;
    }

    public String getRecordID() {
        return recordID;
    }

    public void setRecordID(String recordID) {
        this.recordID = recordID;
    }

    public int getTotalvote() {
        return totalvote;
    }

    public void setTotalvote(int totalvote) {
        this.totalvote = totalvote;
    }

    public int getRepvote() {
        return repvote;
    }

    public void setRepvote(int repvote) {
        this.repvote = repvote;
    }

    public int getDemvote() {
        return demvote;
    }

    public void setDemvote(int demvote) {
        this.demvote = demvote;
    }



}
