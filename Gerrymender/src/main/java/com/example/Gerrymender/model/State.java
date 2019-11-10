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
    @Column(name="whitePop")
    private long white_pop;
    @Column(name="africanAmericanPop")
    private long africanAmerican_pop;
    @Column(name="hispanicPop")
    private long hispanic_pop;
    @Column(name="asianPop")
    private long asian_pop;
    @Column(name="nativePop")
    private long nativeAmerican_pop;

    /*WHITE, AFRICAN_AMERICAN,HISPANIC,ASIAN,NATIVE_AMERICAN*/


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

    public long getNativeAmerican_pop() {
        return nativeAmerican_pop;
    }

    public void setNativeAmerican_pop(long nativeAmerican_pop) {
        this.nativeAmerican_pop = nativeAmerican_pop;
    }

    public long getAsian_pop() {
        return asian_pop;
    }

    public void setAsian_pop(long asian_pop) {
        this.asian_pop = asian_pop;
    }

    public long getHispanic_pop() {
        return hispanic_pop;
    }

    public void setHispanic_pop(long hispanic_pop) {
        this.hispanic_pop = hispanic_pop;
    }

    public long getAfricanAmerican_pop() {
        return africanAmerican_pop;
    }

    public void setAfricanAmerican_pop(long africanAmerican_pop) {
        this.africanAmerican_pop = africanAmerican_pop;
    }

    public long getWhite_pop() {
        return white_pop;
    }

    public void setWhite_pop(long white_pop) {
        this.white_pop = white_pop;
    }
}
