package com.example.Gerrymender.db_elements;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

/**
 * @Author Benjamin Michalowicz
 * @Version 1.0
 * Simple representation of a District Table for each district in a given state in our setup
 */

@Table(name="District")
@Entity
public class District {

    @Id
    private int id; //Districts have ID's or numbers as opposed to names, based on my research

    private String stateName; //Can't have a state object, as that would bring in circular dependencies.
    private long population;

    private Pol_part pol_part; //Reigning political party

    private float race_perc[] = new float[6];

    public float[] getRace_perc(){
        return race_perc;
    }

    public void setRace_perc(float[] race_perc){
        assert(race_perc.length==5);

        for(float x: race_perc){
            assert(x>50 && x<=100);
        }

        this.race_perc=race_perc;

    }
    public String getStateName(){return stateName;}
    public void setStateName(String stateName){this.stateName=stateName;}

    public int getId() {
        return id;
    }

    public void setName(int id) {
        this.id = id;
    }

    public long getPopulation() {
        return population;
    }

    public void setPopulation(long population) {
        this.population = population;
    }

    public Pol_part getPol_part() {
        return pol_part;
    }

    public void setPol_part(Pol_part pol_part) {
        this.pol_part = pol_part;
    }

    private Set<Precinct> precincts;

    public Set<Precinct> getPrecincts(){return precincts;}
    private void setPrecincts(Set<Precinct> precincts){
        this.precincts= new HashSet<>(precincts);

    }
}
