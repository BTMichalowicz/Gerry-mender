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
            return this.districtid.equalsIgnoreCase(((VoteDisId) obj).districtid) && this.statename.equalsIgnoreCase(((VoteDisId) obj).statename) && this.electionyear.equalsIgnoreCase(((VoteDisId) obj).electionyear) && this.electionname.equalsIgnoreCase(((VoteDisId) obj).electionname);
        }
        return false;
    }
}
