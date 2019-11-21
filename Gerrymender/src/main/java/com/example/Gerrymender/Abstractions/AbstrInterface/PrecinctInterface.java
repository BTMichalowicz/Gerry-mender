package com.example.Gerrymender.Abstractions.AbstrInterface;

import java.util.Set;

public interface PrecinctInterface {

    String getID();

    //Object getGeometry();

    String getOriginalDistrictID();

    Set<String> getNeighborIDs();

    int getPopulation();

    int getGOPVote();

    int getDEMVote();
}
