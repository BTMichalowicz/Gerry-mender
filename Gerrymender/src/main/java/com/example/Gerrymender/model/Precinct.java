package com.example.Gerrymender.model;

import com.example.Gerrymender.Abstractions.Area;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Set;

@Table(name="Precinct")
@Entity
public class Precinct extends Area {



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
