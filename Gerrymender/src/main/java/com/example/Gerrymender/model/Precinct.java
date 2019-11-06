package com.example.Gerrymender.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertNotEquals;

@Table(name="Precinct")
@Entity
public class Precinct{

    @Id //ID Annotation for the subclasses
    @Column(name="precinctId")
    private String nameID; //replaces name and ID in state, precinct, district

    @Column(name="totalPop")
    private long totalPop;
    @Column(name="Pol_Part")
    private Pol_part party;

    @Column(name="raceBreakdown")
    private int[] race_Percentage; //Set valued, perhaps in its own table?



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


    public Pol_part getParty() {
        return party;
    }

    public void setParty(Pol_part party) {
        this.party = party;
    }



    @Id //Composite Key for Precincts to know what district they're part of
    @Column(name="districtID")
    private String districtID; //Can't have a state object, as that would bring in circular dependencies.
    private Set<Race> votingBlockRace;

    public String getDistrictID(){return districtID;}
    public void setDistrictID(String districtID ){this.districtID=districtID;}


    public Set<Race> getVotingBlockRace() {
        return votingBlockRace;
    }

    public void setVotingBlockRace(Set<Race> votingBlockRace) {
        this.votingBlockRace = votingBlockRace;
    }
}
