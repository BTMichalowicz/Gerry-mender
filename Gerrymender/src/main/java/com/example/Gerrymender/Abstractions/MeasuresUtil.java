package com.example.Gerrymender.Abstractions;
import com.example.Gerrymender.model.Pol_part;

public class MeasuresUtil {

    public static double calculateMeasure(Measure measure, BaseDistrict district) {

        return measure.calculateMeasure(district);
        /*switch (measure) {
            case PARTISAN_FAIRNESS:
                return calculatePartisanFairness(district);
            case EDGE_COMPACTNESS:
            case REOCK_COMPACTNESS:
            case CONVEX_HULL_COMPACTNESS:
                return measure.calculateMeasure(district);
            case EFFICIENCY_GAP:
                return calculateEfficiencyGap(district);
            case POPULATION_EQUALITY:
                return calculatePopulationEquality(district);
            case COMPETITIVENESS:
                return calculateCompetitiveness(district);
            case GERRYMANDER_REPUBLICAN:
                return calculateGerryManderGOP(district);
            case POPULATION_HOMOGENEITY:
                return calculatePopulationHomogeneity(district);
            case GERRYMANDER_DEMOCRAT:
                return calculateGerryManderDEM(district);
            default:
                return -1;
        }*/
    }
    /*
     * Partisan fairness:
     * 100% - underrepresented Pol_part's winning margin
     * OR
     * underrepresented Pol_part's losing margin
    */
 /*   public static double calculatePartisanFairness(BaseDistrict d) {
        // Temporary section
        int totalVote = 0;
        int totalGOPvote = 0;
        int totalDistricts = 0;
        int totalGOPDistricts = 0;
        BaseState state = d.getState();

        for (BaseDistrict sd : state.getDistricts()) {
            totalVote += sd.getVotes().get(Pol_part.REPUBLICAN);
            totalVote += sd.getVotes().get(Pol_part.DEMOCRAT);
            totalGOPvote += sd.getVotes().get(Pol_part.REPUBLICAN);
            totalDistricts += 1;
            if (sd.getVotes().get(Pol_part.REPUBLICAN) > sd.getVotes().get(Pol_part.DEMOCRAT)) {
                totalGOPDistricts += 1;
            }
        }
        int idealDistrictChange = ((int) Math.round(totalDistricts * ((1.0 * totalGOPvote) / totalVote))) - totalGOPDistricts;
        // End temporary section

        if (idealDistrictChange == 0) { return 1.0; }

        int gv = d.getVotes().get(Pol_part.REPUBLICAN);
        int dv = d.getVotes().get(Pol_part.DEMOCRAT);
        int tv = gv + dv;
        int margin = gv - dv;

        if (tv == 0) { return 1.0; }

        int win_v = Math.max(gv, dv);
        int loss_v = Math.min(gv, dv);
        int inefficient_V;

        if (idealDistrictChange * margin > 0) { inefficient_V = win_v - loss_v; }
        else { inefficient_V = loss_v; }
        return 1.0 - ((inefficient_V * 1.0) / tv);
    }

    public static double calculateCompactness(BaseDistrict d) {
        double internalEdges = d.getInternalEdges();
        double totalEdges = internalEdges + d.getExternalEdges();
        return internalEdges / totalEdges;
    }

    public static double calculateEfficiencyGap(BaseDistrict d) {
        int iv_g = 0;
        int iv_d = 0;
        int tv = 0;
        BaseState state = d.getState();

        for (BaseDistrict sd : state.getDistricts()) {
            int gv = d.getVotes().get(Pol_part.REPUBLICAN);
            int dv = d.getVotes().get(Pol_part.DEMOCRAT);
            if (gv > dv) {
                iv_d += dv;
                iv_g += (gv - dv);
            } else if (dv > gv) {
                iv_g += gv;
                iv_d += (dv - gv);
            }
            tv += gv;
            tv += dv;
        }
        return 1.0 - ((Math.abs(iv_g - iv_d) * 1.0) / tv);
    }

    public static double calculatePopulationEquality(BaseDistrict d) {
        BaseState state = d.getState();
        int idealPopulation = state.getPopulation() / state.getDistricts().size();
        int truePopulation = d.getPopulation();
        if (idealPopulation >= truePopulation) {
            return ((double) truePopulation) / idealPopulation;
        }
        return ((double) idealPopulation) / truePopulation;
    }

    *//*
    COMPETITIVENESS:
    1.0 - margin of victory
    *//*
    public static double calculateCompetitiveness(BaseDistrict d) {
        int gv = d.getVotes().get(Pol_part.REPUBLICAN);
        int dv = d.getVotes().get(Pol_part.DEMOCRAT);
        return 1.0 - (Math.abs(gv - dv) / (gv + dv));
    }

    public static double calculateGerryManderGOP(BaseDistrict d) {
        int gv = d.getVotes().get(Pol_part.REPUBLICAN);
        int dv = d.getVotes().get(Pol_part.DEMOCRAT);
        int tv = gv + dv;
        int margin = gv - dv;
        if (tv == 0) {
            return 1.0;
        }
        int win_v = Math.max(gv, dv);
        int loss_v = Math.min(gv, dv);
        int inefficient_V;
        if (margin > 0) {
            inefficient_V = win_v - loss_v;
        } else {
            inefficient_V = loss_v;
        }
        return 1.0 - ((inefficient_V * 1.0) / tv);
    }

    //calculate square error of population, normalized to 0,1
    public static double calculatePopulationHomogeneity(BaseDistrict district) {
        if (district.getPrecincts().size() == 0)
            return 0;
        double sum = district.getPrecincts().stream().mapToDouble(BasePrecinct::getPopulation).sum();
        final double mean = sum / district.getPrecincts().size();
        double sqError = district.getPrecincts()
                .stream().mapToDouble(
                        (precinct) -> (Math.pow(precinct.getPopulation() - mean, 2))
                ).sum();
        sqError /= (district.getPrecincts().size());
        double averagePopulation = 2000;
        return Math.tanh(sqError / (averagePopulation * 200));
    }

    public static double calculateGerryManderDEM(BaseDistrict d) {
        int gv = d.getVotes().get(Pol_part.REPUBLICAN);
        int dv = d.getVotes().get(Pol_part.DEMOCRAT);
        int tv = gv + dv;
        int margin = dv - gv;
        if (tv == 0) {
            return 1.0;
        }
        int win_v = Math.max(gv, dv);
        int loss_v = Math.min(gv, dv);
        int inefficient_V;
        if (margin > 0) {
            inefficient_V = win_v - loss_v;
        } else {
            inefficient_V = loss_v;
        }
        return 1.0 - ((inefficient_V * 1.0) / tv);
    }*/
}
