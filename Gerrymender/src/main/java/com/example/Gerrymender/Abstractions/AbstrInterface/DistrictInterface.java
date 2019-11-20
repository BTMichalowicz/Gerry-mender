package com.example.Gerrymender.Abstractions.AbstrInterface;

import java.util.Set;

public interface DistrictInterface<BasePrecinct extends PrecinctInterface> {
    String getID();
    Set<BasePrecinct> getPrecincts();
    void removePrecinct(BasePrecinct p);
    void addPrecinct(BasePrecinct p);
    Set<BasePrecinct> getBorderPrecincts();
    BasePrecinct getPrecinct(String precinctID);
    default int getPopulation() {
        return 0;
    }
}
