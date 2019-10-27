package com.example.Gerrymender.db_elements;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertNotEquals;

/**
 * @Author Benjamin Michalowicz
 * @Version 1.0
 * Simple representation of a District Table for each district in a given state in our setup
 */

@Table(name="District")
@Entity
public class District {

   public String stateName;
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
