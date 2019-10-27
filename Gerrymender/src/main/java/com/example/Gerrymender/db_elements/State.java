package com.example.Gerrymender.db_elements;

/* Explanation for State representation
in the database

 */

/**
 * @Author Benjamin Michalowicz
 * @Version 1.0
 * Simple representation of a State Table for each of the three states in our setup
 **/

import com.example.Gerrymender.Abstractions.Area;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.Set;


@Table(name="State")
@Entity
public class State extends Area {

    private Set<District> districts;
    private Set<Precinct> precincts;
    private Set<Cluster> clusters ; //TODO: Define Clusters

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
}
