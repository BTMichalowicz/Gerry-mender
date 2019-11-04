package com.example.Gerrymender.Abstractions;


import com.example.Gerrymender.model.Edge;
import com.example.Gerrymender.model.Pol_part;

import javax.persistence.Column;
import javax.persistence.Id;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertNotEquals;

public abstract class Area {

    private Set<Edge> neighbors;
    @Id //ID Annotation for the subclasses
    @Column(name="areaID")
    private String nameID; //replaces name and ID in state, precinct, district

    @Column(name="totalPop")
    private long totalPop;
    @Column(name="Pol_Part")
    private Pol_part party;

    @Column(name="raceBreakdown")
    private int[] race_Percentage; //Set valued, perhaps in its own table?


    public Set<Edge> getNeighbors() {
        return neighbors;
    }

    public void setNeighbors(Set<Edge> neighbors) {
        this.neighbors = neighbors;
    }

    public String getNameID() {
        return nameID;
    }

    public void setNameID(String nameID) {
        assertNotEquals(nameID, "");
        this.nameID = nameID;
    }

    public long getTotalPop() {
        return totalPop;
    }

    public void setTotalPop(long totalPop) {
        assert(totalPop>0);
        this.totalPop = totalPop;
    }

    public Algorithm algo;

    public Pol_part getParty() {
        return party;
    }

    public void setParty(Pol_part party) {
        this.party = party;
    }
}
