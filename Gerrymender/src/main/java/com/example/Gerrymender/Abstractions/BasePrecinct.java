package com.example.Gerrymender.Abstractions;

import com.example.Gerrymender.Abstractions.AbstrInterface.PrecinctInterface;
import com.example.Gerrymender.model.Pol_part;
import com.example.Gerrymender.model.Precinct;
import com.example.Gerrymender.model.Race;

import org.locationtech.jts.geom.Geometry;
import org.locationtech.jts.geom.Polygon;

import java.util.*;




public class BasePrecinct implements PrecinctInterface {

    private String ID;
    //private int population;
    private Map<Pol_part, Integer> votes;
    private VotingBloc bloc;
    private Race majorityRace;
    private int majorityRacePop;
    private int clusterId;
    private Set<BasePrecinct> edges;


    //New File imputs
    private Geometry geometry;
    private String geometryJSON;
    private String originalDistrictID;
    private int population;
    private int gop_vote;
    private int dem_vote;
    private Set<String> neighborIDs;

    public BasePrecinct (Precinct p) {
            this.ID = p.getNameID();
            this.neighborIDs = new HashSet<String>(Arrays.asList(p.getNeighbors().split(",")));
            this.population = (int)p.getTotalPop();

    }

    public BasePrecinct(
            String ID,
            Geometry geometry,
            String geometryJSON,
            String districtID,
            int population,
            int gop_vote,
            int dem_vote,
            Set<String> neighborIDs) {
        this.ID = ID;
        this.geometry = geometry;
        this.geometryJSON = geometryJSON;
        this.originalDistrictID = districtID;
        this.population = population;
        this.gop_vote = gop_vote;
        this.dem_vote = dem_vote;
        this.neighborIDs = neighborIDs;
    }

    @Override
    public String getID() {
        return ID;
    }

    public String getGeometryJSON() {
        return geometryJSON;
    }

    public Geometry getGeometry() {
        return geometry;
    }


    public double getPopulationDensity() {
        if (geometry !=null && geometry.getArea() != 0)
            return getPopulation() / geometry.getArea();
        return -1;
    }


    @Override
    public String getOriginalDistrictID() {
        return originalDistrictID;
    }

    @Override
    public Set<String> getNeighborIDs() {
        return neighborIDs;
    }

    @Override
    public int getPopulation() {
        return population;
    }

    @Override
    public int getGOPVote() {
        return gop_vote;
    }

    @Override
    public int getDEMVote() {
        return dem_vote;
    }

    //END New file


    public VotingBloc getBloc() {
        return bloc;
    }

    public void setBloc(VotingBloc bloc) {
        this.bloc = bloc;
    }

    public Map<Pol_part, Integer> getVotes() {
        return votes;
    }

    public void setVotes(Map<Pol_part, Integer> votes) {
        this.votes = votes;
    }


    public int getMajorityRacePop() { return majorityRacePop; }

    public Race getMajorityRace() { return majorityRace;    }

    public void setMajorityRace(Race majorityRace) { this.majorityRace = majorityRace;    }

    public void setMajorityRacePop(int majorityRacePop) { this.majorityRacePop = majorityRacePop;    }

    public int getClusterId() { return clusterId;    }

    public void setClusterId(int clusterId) { this.clusterId = clusterId;    }

    public Set<BasePrecinct> getEdges() { return edges;    }

    public void setEdges(Set<BasePrecinct> edges) { this.edges = edges;    }
}