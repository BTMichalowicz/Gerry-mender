package com.example.Gerrymender.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertNotEquals;

@Table(name="Precinct")
@Entity
public class Precinct implements Serializable {

    private static final long SerializeID = 2L; //or some other ID

    @Id //ID Annotation for the subclasses
    @Column
    private String precinctid; //replaces name and ID in state, precinct, district

    @Column
    private long totalpop;
    @Column
    private String party;

    @Column
    private String countyname;


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

    public void setNativeAmerican_pop(long nativeamericanpop) {
        this.nativeamericanpop = nativeamericanpop;
    }

    public long getAsian_pop() {
        return asianpop;
    }

    public void setAsian_pop(long asianpop) {
        this.asianpop = asianpop;
    }

    public long getHispanic_pop() {
        return hispanicpop;
    }

    public void setHispanic_pop(long hispanicpop) {
        this.hispanicpop = hispanicpop;
    }

    public long getAfricanAmerican_pop() {
        return africanamericanpop;
    }

    public void setAfricanAmerican_pop(long africanamericanpop) {
        this.africanamericanpop = africanamericanpop;
    }

    public long getWhite_pop() {
        return whitepop;
    }

    public void setWhite_pop(long white_pop) {
        this.whitepop = white_pop;
    }


    public String getNameID() {
        return precinctid;
    }

    public void setNameID(String nameID) {
        assertNotEquals(nameID, "");
        this.precinctid = nameID;
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



    //Composite Key for Precincts to know what district they're part of
    @Column
    private String districtid; //Can't have a state object, as that would bring in circular dependencies.



    public String getDistrictID(){return districtid;}
    public void setDistrictID(String districtID ){this.districtid=districtID;}


    public String getCountyName() {
        return countyname;
    }

    public void setCountyName(String countyName) {
        this.countyname = countyName;
    }
}
