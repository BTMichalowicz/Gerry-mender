package com.example.Gerrymender.Abstractions.AbstrInterface;


import java.util.HashSet;
import java.util.Set;

public interface MeasureFunction<Precinct extends PrecinctInterface, District extends DistrictInterface<Precinct>> {
    double calculateMeasure(District district);

    default Set<MeasureFunction<Precinct, District>> subMeasures() {
        return new HashSet<>();
    }
}

}
