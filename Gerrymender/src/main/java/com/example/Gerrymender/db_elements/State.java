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

    private float perc_hispanic;
    private float perc_white;
    private float perc_afr_amer;
    private float perc_asian;


    public float getPerc_hispanic() {
        return perc_hispanic;
    }

    public void setPerc_hispanic(float perc_hispanic) {
        this.perc_hispanic = perc_hispanic;
    }

    public float getPerc_asian() {
        return perc_asian;
    }

    public void setPerc_asian(float perc_asian) {
        this.perc_asian = perc_asian;
    }

    public float getPerc_afr_amer() {
        return perc_afr_amer;
    }

    public void setPerc_afr_amer(float perc_afr_amer) {
        this.perc_afr_amer = perc_afr_amer;
    }

    public float getPerc_white() {
        return perc_white;
    }

    public void setPerc_white(float perc_white) {
        this.perc_white = perc_white;
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
