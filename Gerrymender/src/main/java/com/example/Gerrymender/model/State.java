package com.example.Gerrymender.model;

/* Explanation for State representation
in the database

 */
import com.example.Gerrymender.Abstractions.Algorithm;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.ArrayList;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertNotEquals;

@Table(name="State")
@Entity
public class State{
    @Id //ID Annotation for the subclasses
    @Column
    private String statename; //replaces name and ID in state, precinct, district
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

    public String getNameID() {
        return statename;
    }
    public void setNameID(String stateName) {
        assertNotEquals(stateName, "");
        this.statename = stateName;
    }

    public long getTotalPop() {
        return totalpop;
    }
    public void setTotalPop(long totalPop) {
        assert(totalPop>0);
        this.totalpop = totalPop;
    }

   // public Algorithm algo;
    public String getParty() {
        return party;
    }
    public void setParty(String party) {
        this.party = party;
    }

    /*private ArrayList<District> districts;
      private ArrayList<Precinct> precincts;
      private ArrayList<Cluster> clusters ; //TODO: Define Clusters
      private ArrayList<Precinct> hasVotingBloc;

      public ArrayList<District> getDistricts(){return districts;}
      public void setDistricts(ArrayList<District> districts){ this.districts= districts; }

      public ArrayList<Cluster> getClusters() { return clusters; }
      public void setClusters(ArrayList<Cluster> clusters) { this.clusters = clusters; }

      public ArrayList<Precinct> getPrecincts() { return precincts;}
      public void setPrecincts(ArrayList<Precinct> precincts) { this.precincts = precincts; }

      public ArrayList<Precinct> getHasVotingBloc() { return hasVotingBloc; }
      public void setHasVotingBloc(ArrayList<Precinct> hasVotingBloc) { this.hasVotingBloc = hasVotingBloc; }

      public void addVotingBloc(Precinct p) { if (!hasVotingBloc.contains(p)) { hasVotingBloc.add(p); } }

    public long getNativeAmerican_pop() { return nativeamericanpop; }
    public void setNativeAmerican_pop(long nativeAmerican_pop) { this.nativeamericanpop = nativeAmerican_pop;}

    public long getAsian_pop() { return asianpop; }
    public void setAsian_pop(long asian_pop) { this.asianpop = asian_pop; }

    public long getHispanic_pop() { return hispanicpop; }
    public void setHispanic_pop(long hispanic_pop) { this.hispanicpop = hispanic_pop; }

    public long getAfricanAmerican_pop() { return africanamericanpop; }
    public void setAfricanAmerican_pop(long africanAmerican_pop) { this.africanamericanpop = africanAmerican_pop; }

    public long getWhite_pop() { return whitepop; }

    public void setWhite_pop(long white_pop) { this.whitepop = white_pop; }
}*/
}