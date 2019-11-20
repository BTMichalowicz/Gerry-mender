package com.example.Gerrymender.Abstractions;

import com.example.Gerrymender.Abstractions.AbstrInterface.DistrictInterface;
import com.example.Gerrymender.Abstractions.AbstrInterface.PrecinctInterface;
import com.example.Gerrymender.model.Pol_part;
import org.locationtech.jts.algorithm.MinimumBoundingCircle;
import org.locationtech.jts.geom.Geometry;
import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.geom.MultiPolygon;
import org.locationtech.jts.geom.Polygon;

import java.net.Inet4Address;

import java.util.*;

public class BaseDistrict implements DistrictInterface<BasePrecinct> {
    private String ID;
    private BaseState state;
    private int population;
    private int internalEdges = 0;
    private int externalEdges = 0;
    private Map<Pol_part, Integer> votes;
    private HashMap<String, BasePrecinct> precincts;


    //From new file that wasn't already here
    private static final int MAXX = 0;
    private static final int MAXY = 1;
    private static final int MINX = 2;
    private static final int MINY = 3;
    private int gop_vote;
    private int dem_vote;

    private Set<BasePrecinct> borderPrecincts;

    private MultiPolygon multiPolygon;

    private Geometry boundingCircle;
    private Geometry convexHull;

    private boolean boundingCircleUpdated=false;
    private boolean multiPolygonUpdated=false;
    private boolean convexHullUpdated=false;


    public BaseDistrict(String ID, BaseState state ) {
        this.ID = ID;
        population = 0;
        gop_vote = 0;
        dem_vote = 0;
        precincts = new HashMap<String, BasePrecinct>();
        borderPrecincts = new HashSet<BasePrecinct>();
        this.state = state;
    }
    public Map<Pol_part, Integer> getVotes() {
        return votes;
    }





    //NEW FILE
    public Set<BasePrecinct> getPrecincts() {
        Set<BasePrecinct> to_return = new HashSet<BasePrecinct>();
        for (BasePrecinct p : precincts.values()) {
            to_return.add(p);
        }
        return to_return;
    }

    public Set<BasePrecinct> getBorderPrecincts() {
        return new HashSet<>(borderPrecincts);
    }

    public boolean isBorderPrecinct(BasePrecinct precinct) {
        for (String neighborID : precinct.getNeighborIDs()) {
            //if the neighbor's neighbor is not in the district, then it is outer
            if (!precincts.containsKey(neighborID))  {
                return true;
            }
        }
        return false;
    }

    public BasePrecinct getPrecinct(String PrecID) {
        return precincts.get(PrecID);
    }

    public void addPrecinct(BasePrecinct p) {
        precincts.put(p.getID(), p);
        population += p.getPopulation();
        gop_vote += p.getGOPVote();
        dem_vote += p.getDEMVote();
        borderPrecincts.add(p);
        Set<BasePrecinct> newInternalNeighbors = getInternalNeighbors(p);
        int newInternalEdges = newInternalNeighbors.size();
        internalEdges += newInternalEdges;
        externalEdges -= newInternalEdges;
        externalEdges += (p.getNeighborIDs().size() - newInternalEdges);
        newInternalNeighbors.removeIf(
                this::isBorderPrecinct
        );
        borderPrecincts.removeAll(newInternalNeighbors);

        this.multiPolygonUpdated = false;
        this.convexHullUpdated = false;
        this.boundingCircleUpdated = false;
    }

    public void removePrecinct(BasePrecinct p) {
        precincts.remove(p.getID());
        population -= p.getPopulation();
        gop_vote -= p.getGOPVote();
        dem_vote -= p.getDEMVote();
        Set<BasePrecinct> lostInternalNeighbors = getInternalNeighbors(p);
        int lostInternalEdges = lostInternalNeighbors.size();
        internalEdges -= lostInternalEdges;
        externalEdges += lostInternalEdges;
        externalEdges -= (p.getNeighborIDs().size() - lostInternalEdges);
        borderPrecincts.remove(p);
        borderPrecincts.addAll(lostInternalNeighbors);

        this.multiPolygonUpdated = false;
        this.convexHullUpdated = false;
        this.boundingCircleUpdated = false;
    }


    public MultiPolygon computeMulti() {
        Polygon[] polygons = new Polygon[getPrecincts().size()];

        Iterator<BasePrecinct> piter = getPrecincts().iterator();
        for(int ii = 0; ii < polygons.length; ii++) {
            Geometry poly = piter.next().getGeometry();
            if (poly instanceof Polygon)
                polygons[ii] = (Polygon) poly;
            else
                polygons[ii] = (Polygon) poly.convexHull();
        }
        MultiPolygon mp = new MultiPolygon(polygons,new GeometryFactory());
        this.multiPolygon = mp;
        this.multiPolygonUpdated = true;
        return mp;
    }

    public MultiPolygon getMulti() {
        if (this.multiPolygonUpdated && this.multiPolygon != null)
            return this.multiPolygon;
        return computeMulti();
    }

    public Geometry getConvexHull() {
        if (convexHullUpdated && convexHull !=null)
            return convexHull;
        convexHull = multiPolygon.convexHull();
        this.convexHullUpdated = true;
        return convexHull;
    }

    public Geometry getBoundingCircle() {
        if (boundingCircleUpdated && boundingCircle !=null)
            return boundingCircle;
        boundingCircle = new MinimumBoundingCircle(getMulti()).getCircle();
        this.boundingCircleUpdated = true;
        return boundingCircle;
    }


    public void setState(BaseState state) {
        this.state = state;
    }

    public BaseState getState() {
        return state;
    }

    public String getID() {
        return ID;
    }

    public int getPopulation() {
        return population;
    }

    public int getGOPVote() {
        return gop_vote;
    }

    public int getDEMVote() {
        return dem_vote;
    }

    public int getInternalEdges() {
        return internalEdges;
    }

    public int getExternalEdges() {
        return externalEdges;
    }

    private Set<BasePrecinct> getInternalNeighbors(BasePrecinct p) {
        Set<BasePrecinct> neighborsInternal = new HashSet<>();
        for (String nid : p.getNeighborIDs()) {
            if (precincts.containsKey(nid)) {
                BasePrecinct neighbor = precincts.get(nid);
                neighborsInternal.add(neighbor);
            }
        }
        return neighborsInternal;
    }

}