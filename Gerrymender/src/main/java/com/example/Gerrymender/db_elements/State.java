package com.example.Gerrymender.db_elements;

/* Explanation for State representation
in the database

 */

/**
 * @Author Benjamin Michalowicz
 * @Version 1.0
 * Simple representation of a State Table for each of the three states in our setup
 */

import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.ArrayList;


@Entity
public class State {

    @Id
    private String name; //Name, our key


    private long population; //population
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



    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    private ArrayList<District> districts;
}
