package com.example.Gerrymender.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.ArrayList;
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
public class District implements Serializable {

    private static final long SerializeID = 22L; //or some other ID


    @Id //ID Annotation for the subclasses
    @Column
    private String districtid; //replaces name and ID in state, precinct, district

    @Column
    private long totalpop;
    @Column
    private String party;


    @Column
    private long whitepop;
    @Column
    private long africanamericanpop;
    @Column
    private long hispanicpop;
    @Column
    private long asianpop;
    @Column
    private long nativeamericanpop;

    public long getNativeAmerican_pop() {
        return nativeamericanpop;
    }

    public void setNativeAmerican_pop(long nativeAmerican_pop) {
        this.nativeamericanpop = nativeAmerican_pop;
    }

    public long getAsian_pop() {
        return asianpop;
    }

    public void setAsian_pop(long asian_pop) {
        this.asianpop = asian_pop;
    }

    public long getHispanic_pop() {
        return hispanicpop;
    }

    public void setHispanic_pop(long hispanic_pop) {
        this.hispanicpop = hispanic_pop;
    }

    public long getAfricanAmerican_pop() {
        return africanamericanpop;
    }

    public void setAfricanAmerican_pop(long africanAmerican_pop) {
        this.africanamericanpop = africanAmerican_pop;
    }

    public long getWhite_pop() {
        return whitepop;
    }

    public void setWhite_pop(long white_pop) {
        this.whitepop = white_pop;
    }



    public String getNameID() {
        return districtid;
    }

    public void setNameID(String nameID) {
        assertNotEquals(nameID, "");
        this.districtid = nameID;
    }

    public long getTotalPop() {
        return totalpop;
    }

    public void setTotalPop(long totalPop) {
        assert(totalPop>0);
        this.totalpop = totalPop;
    }

    public String getParty() {
        return party;
    }

    public void setParty(String party) {
        this.party = party;
    }


     //Composite Key for Districts
    @Column
   private String statename;

    public String getStateName(){return statename;}
    public void setStateName(String stateName){
        assertNotEquals(stateName, "");
        assert(stateName!=null);
        this.statename=stateName;
    }



//    private ArrayList<Precinct> precincts;
//
//    public ArrayList<Precinct> getPrecincts(){return precincts;}
//    private void setPrecincts(Set<Precinct> precincts){
//        assert(precincts!=null);
//        this.precincts= new ArrayList<>(precincts);
//
//    }
}
