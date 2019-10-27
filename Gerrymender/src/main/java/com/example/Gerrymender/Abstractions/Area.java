package com.example.Gerrymender.Abstractions;

import com.example.Gerrymender.db_elements.Edge;
import com.example.Gerrymender.db_elements.Pol_part;

import javax.persistence.Id;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertNotEquals;

public abstract class Area {

    private Set<Edge> neighbors;
    @Id //ID Annotation for the subclasses
    private String nameID; //replaces name and ID in state, precinct, district
    private long totalPop;
    private Pol_part party;

    private int[] race_Percentage;


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
