package com.example.Gerrymender.model;

import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
public class VoteId  implements Serializable {
    public String precinctid;
    public String electionyear;
    public String electionname;

    public VoteId () {
        this.electionname = "";
        this.electionyear = "";
        this.precinctid = "";
    }

    public VoteId(String precinctid, String electionyear, String electionname) {
        this.precinctid = precinctid;
        this.electionyear = electionyear;
        this.electionname = electionname;
    }

    @Override
    public boolean equals(Object obj) {
        if(obj instanceof VoteId) {
            return precinctid == ((VoteId) obj).precinctid && electionyear == ((VoteId) obj).electionyear && electionname == ((VoteId) obj).electionname;
        }
        return false;
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }
}
