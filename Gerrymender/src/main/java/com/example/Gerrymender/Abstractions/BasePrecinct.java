package com.example.Gerrymender.Abstractions;
import com.example.Gerrymender.Abstractions.AbstrInterface.PrecinctInterface;
import com.example.Gerrymender.model.Pol_part;
import com.example.Gerrymender.model.Precinct;
import com.example.Gerrymender.model.Race;
import com.sun.xml.messaging.saaj.util.ByteInputStream;
import org.geotools.geojson.geom.GeometryJSON;
import org.locationtech.jts.geom.Geometry;
import org.geotools.*;
import org.locationtech.jts.geom.MultiPolygon;
import org.locationtech.jts.geom.Polygon;
import java.io.*;
import java.util.*;

public class BasePrecinct implements PrecinctInterface {
    private String ID;
    private Map<String, Votes> votes;
    private VotingBloc bloc;
    private Race majorityRace;
    private int majorityRacePop;
    private int clusterId;
    private Set<BasePrecinct> edges;
    private int[] racePops;
    private String countyName;
    //New File imputs
    private Geometry geometry;
    private String geometryJSON;
    private String originalDistrictID;
    private int population;
    private int gop_vote;
    private int dem_vote;
    private Set<String> neighborIDs;
    private String points;

    public BasePrecinct(Precinct p, Map<String, Votes> v) {
        this.ID = p.getNameID();
        this.neighborIDs = new HashSet<String>(Arrays.asList(p.getNeighbors().split(",")));
        this.population = (int) p.getTotalPop();
        this.votes = v;
        this.racePops = new int[]{(int) p.getWhite_pop(), (int) p.getAfricanAmerican_pop(), (int) p.getHispanic_pop(), (int) p.getAsian_pop(), (int) p.getNativeAmerican_pop()};
        int maxPop = 0;
        Race maxRace = Race.WHITE;
        for (int i = 0; i < racePops.length; i++) {
            if (racePops[i] > maxPop) {
                maxPop = racePops[i];
                maxRace = Race.values()[i];
            }
            this.majorityRacePop = maxPop;
            this.majorityRace = maxRace;
            this.edges = new HashSet<BasePrecinct>();
            countyName = p.getCountyName();
        }
        this.majorityRacePop = maxPop;
        this.majorityRace = maxRace;
        this.edges = new HashSet<BasePrecinct>();
        countyName = p.getCountyName();
        this.points = p.getPoints();
        //this.geometry = JSONToGeometry(this.points);
    }
    public BasePrecinct(
            String ID,
            Geometry geometry,
            String geometryJSON,
            String districtID,
            int population,
            int gop_vote,
            int dem_vote,
            Set<String> neighborIDs,
            String points) {
        this.ID = ID;
        this.geometry = geometry;
        this.geometryJSON = geometryJSON;
        this.originalDistrictID = districtID;
        this.population = population;
        this.gop_vote = gop_vote;
        this.dem_vote = dem_vote;
        this.neighborIDs = neighborIDs;
        this.points = points;
        //this.geometry = JSONToGeometry(points);
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
        if (geometry != null && geometry.getArea() != 0)
            return getPopulation() / geometry.getArea();
        return -1;
    }
    public String getCountyName() {
        return countyName;
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
    public String getPoints() {
        return points;
    }
    public void setPoints(String points) {
        this.points = points;
    }
    public VotingBloc getBloc() {
        return bloc;
    }
    public void setBloc(VotingBloc bloc) {
        this.bloc = bloc;
    }
    public Map<String, Votes> getVotes() {
        return votes;
    }
    public void setVotes(Map<String, Votes> votes) {
        this.votes = votes;
    }
    public int getMajorityRacePop() {
        return majorityRacePop;
    }
    public Race getMajorityRace() {
        return majorityRace;
    }
    public void setMajorityRace(Race majorityRace) {
        this.majorityRace = majorityRace;
    }
    public void setMajorityRacePop(int majorityRacePop) {
        this.majorityRacePop = majorityRacePop;
    }
    public int getClusterId() {
        return clusterId;
    }
    public void setClusterId(int clusterId) {
        this.clusterId = clusterId;
    }
    public Set<BasePrecinct> getEdges() {
        return edges;
    }
    public void setEdges(Set<BasePrecinct> edges) {
        this.edges = edges;
    }
    public int[] getRacePops() {
        return racePops;
    }
   /* public Geometry JSONToGeometry(String points) {
        Geometry geom = null;
        GeometryJSON geo = new GeometryJSON();
       // Reader pointReader = new StringReader(points);
        InputStream polyInput = new ByteArrayInputStream(points.getBytes());
        try {
            geom = geo.readPolygon(polyInput);
            return geom;
        } catch (IOException e) {
            try {
                geom = geo.readMultiPolygon(polyInput);
                return geom;
            } catch (IOException e3){
                return geom;

            } catch (NullPointerException e2) {
                e2.printStackTrace();
            }
        }
        return null;
    }*/
}