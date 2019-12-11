package com.example.Gerrymender.Abstractions.AbstrInterface;

import com.example.Gerrymender.Abstractions.BaseDistrict;
import com.example.Gerrymender.Abstractions.BasePrecinct;

import java.util.Map;

public interface StateInterface<
        Precinct extends PrecinctInterface,
        District extends DistrictInterface<Precinct>> {
    Map<String, BasePrecinct> getPrecincts();
    Map<String, BaseDistrict> getDistricts();

    Precinct getPrecinct(String precinctId);

    District getDistrict(String precinctId);

    default int getPopulation() {
        return 0;
    }
}
