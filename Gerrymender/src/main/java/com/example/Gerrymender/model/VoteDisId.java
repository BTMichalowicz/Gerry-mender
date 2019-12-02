package com.example.Gerrymender.model;

import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
public class VoteDisId implements Serializable {
    private String districtid;
    private String statename;
    private String electionyear;
    private String electionname;

    public VoteDisId() {
        districtid = "";
        statename = "";
        electionyear = "";
        electionname = "";
    }

    public VoteDisId(String districtid, String statename, String electionyear, String electionname) {
        this.districtid = districtid;
        this.statename = statename;
        this.electionyear = electionyear;
        this.electionname = electionname;
    }

    @Override
    public boolean equals(Object obj) {
        if(obj instanceof VoteDisId) {
            return this.districtid == ((VoteDisId) obj).districtid && this.statename == ((VoteDisId) obj).statename && this.electionyear == ((VoteDisId) obj).electionyear && this.electionname == ((VoteDisId) obj).electionname;
        }
        return false;
    }
}
