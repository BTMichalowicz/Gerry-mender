package com.example.Gerrymender.db_elements;

import javax.persistence.Id;
import java.util.ArrayList;

public class Precinct {

    @Id
    private int id; //Districts have ID's or numbers as opposed to names, based on my research

    private int districtID; //Can't have a state object, as that would bring in circular dependencies.
    private long population;

    private Pol_part pol_part; //Reigning political party

    private float perc_hispanic;
    private float perc_white;
    private float perc_afr_amer;
    private float perc_asian;

    public int getDistrictID(){return districtID;}
    public void setDistrictID(int districtID ){this.districtID=districtID;}


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


}
