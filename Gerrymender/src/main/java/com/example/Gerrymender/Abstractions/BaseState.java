package com.example.Gerrymender.Abstractions;

import com.example.Gerrymender.Abstractions.AbstrInterface.StateInterface;
import com.example.Gerrymender.model.State;
//import edu.stonybrook.politech.annealing.measures.StateInterface;
import java.util.*;

public class BaseState
        implements StateInterface<BasePrecinct, BaseDistrict> {

    private String name;//name is saved for later storage into the database
    private Map<String, BaseCluster> clusters;

    private Map<String, BaseDistrict> districts;
    private Map<String, BasePrecinct> precincts;

    private final int population;

    public BaseState(String name, Set<BasePrecinct> inPrecincts) {
        this.name = name;
        this.districts = new HashMap<>();
        this.precincts = new HashMap<>();
        for (BasePrecinct p : inPrecincts) {
            String districtID = p.getOriginalDistrictID();
            BaseDistrict d = districts.get(districtID);
            if (d == null) {
                d = new BaseDistrict(districtID, this);
                districts.put(districtID, d);
            }
            d.addPrecinct(p);
            this.precincts.put(p.getID(), p);
        }
        this.population = districts.values().stream().mapToInt(BaseDistrict::getPopulation).sum();
    }

    public BaseState(State s) {
        //TODO add converting here
        population = (int)(s.getTotalPop());
    }
    public void setPrecincts(Map<String, BasePrecinct> basePrecincts ) { precincts = basePrecincts; }
    public Map<String, BasePrecinct> getPrecincts() { return precincts; }

    public Map<String, BaseDistrict> getDistricts() { return districts; }

    public BaseDistrict getDistrict(String distID) {
        return districts.get(distID);
    }

    public BasePrecinct getPrecinct(String precID) {
        return precincts.get(precID);
    }

    @Override
    public int getPopulation() {
        return population;
    }

    public Map<String, BaseCluster> getClusters() {
        return clusters;
    }

    public void setClusters(Map<String, BaseCluster> clusters) {
        this.clusters = clusters;
    }
}