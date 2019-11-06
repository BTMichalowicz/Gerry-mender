package com.example.Gerrymender.model;

/* Explanation for State representation
in the database

 */

/**
 * @Author Benjamin Michalowicz
 * @Version 1.0
 * Simple representation of a State Table for each of the three states in our setup
 **/

import com.example.Gerrymender.Abstractions.Algorithm;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertNotEquals;


@Table(name="State")
@Entity
public class State{

    @Id //ID Annotation for the subclasses
    @Column(name="stateName")
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

    public Algorithm algo;

    public Pol_part getParty() {
        return party;
    }

    public void setParty(Pol_part party) {
        this.party = party;
    }

    private Set<District> districts;
    private Set<Precinct> precincts;
    private Set<Cluster> clusters ; //TODO: Define Clusters
    private Set<Precinct> hasVotingBloc;

    public Set<District> getDistricts(){return districts;}
    public void setDistricts(Set<District> districts){
        this.districts= districts;
    }

    public Set<Cluster> getClusters() {
        return clusters;
    }

    public void setClusters(Set<Cluster> clusters) {
        this.clusters = clusters;
    }

    public Set<Precinct> getPrecincts() {
        return precincts;
    }

    public void setPrecincts(Set<Precinct> precincts) {
        this.precincts = precincts;
    }


    public Set<Precinct> getHasVotingBloc() {
        return hasVotingBloc;
    }

    public void setHasVotingBloc(Set<Precinct> hasVotingBloc) {
        this.hasVotingBloc = hasVotingBloc;
    }


    public void addVotingBloc(Precinct p) {
        if (!hasVotingBloc.contains(p)) {
            hasVotingBloc.add(p);
        }
    }
}
