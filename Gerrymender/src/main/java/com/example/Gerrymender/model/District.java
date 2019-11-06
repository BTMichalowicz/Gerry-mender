package com.example.Gerrymender.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertNotEquals;

/**
 * @author Benjamin Michalowicz
 * @version 1.0
 * Simple representation of a District Table for each district in a given state in our setup
 */

@Table(name="District")
@Entity
public class District{

    @Id //ID Annotation for the subclasses
    @Column(name="districtID")
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

    public int[] getRace_Percentage() {
        return race_Percentage;
    }

    public void setRace_Percentage(int[] race_Percentage) {
        assert(race_Percentage!=null);

        int sum = 0;
        for(int i : race_Percentage){
            assert(i>0);
            sum+=i;
        }

        assert(sum==100);
        this.race_Percentage = race_Percentage;
    }

    @Id //Composite Key for Districts
    @Column(name="StateName")
   private String stateName;

    public String getStateName(){return stateName;}
    public void setStateName(String stateName){
        assertNotEquals(stateName, "");
        assert(stateName!=null);
        this.stateName=stateName;
    }



    private Set<Precinct> precincts;

    public Set<Precinct> getPrecincts(){return precincts;}
    private void setPrecincts(Set<Precinct> precincts){
        assert(precincts!=null);
        this.precincts= new HashSet<>(precincts);

    }
}
