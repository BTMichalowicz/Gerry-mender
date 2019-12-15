package com.example.Gerrymender.Abstractions;

import java.util.Map;
import java.util.Set;

public class MyAlgorithm extends Algorithm {

    public MyAlgorithm(BaseState BaseState, DefaultMeasures measures) {
        super(BaseState, measures);
    }
    public MyAlgorithm() {super();}

    public String describeDistrict(BaseDistrict d) {
        String to_return = "{ \"ID\": \"" + d.getID() + "\", \"MEASURES\": [";
        boolean first = true;
        Set<Measure> measures = (Set) getDistrictScoreFunction().subMeasures();
        for (Measure m : measures) {
            try {
                if (first) {
                    first = false;
                } else {
                    to_return += ", ";
                }
                double rating = m.calculateMeasure(d);
                to_return += "{ \"MEASURE\": \"" + m.name() + "\", ";
                to_return +=  "\"SCORE\": " + (rating * 100) + " }";
            } catch (Exception e) {
                System.out.println(m.name() + " - " + e.getClass().getCanonicalName() + " - Message:");
                System.out.println(e.getMessage());
                return "ERROR";
            }
        }
        to_return += "] }";
        return to_return;
    }

    public void setWeights(Map<Measure, Double> weights) {
        // TODO -- this should NOT be typecasted !! ! !. We know people will only use this with default measures, but its still a meh casting
        System.out.println(weights);
        ((DefaultMeasures) getDistrictScoreFunction()).updateConstantWeights(weights);
        updateScores();
    }
}
