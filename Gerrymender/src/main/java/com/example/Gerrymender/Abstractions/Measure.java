package com.example.Gerrymender.Abstractions;

import com.example.Gerrymender.Abstractions.AbstrInterface.MeasureFunction;
import org.locationtech.jts.geom.Geometry;
import org.locationtech.jts.geom.MultiPolygon;

public enum Measure implements MeasureFunction<BasePrecinct, BaseDistrict> {
    PARTISAN_FAIRNESS {
        /**
         * Partisan fairness:
         * 100% - underrepresented party's winning margin
         * OR
         * underrepresented party's losing margin
         * (We want our underrepresented party to either win by a little or lose by a lot - fewer wasted votes)
         */
        public double calculateMeasure(BaseDistrict d) {
            // Temporary section
            int totalVote = 0;
            int totalGOPvote = 0;
            int totalDistricts = 0;
            int totalGOPDistricts = 0;
            BaseState BaseState = d.getState();
            for (BaseDistrict sd : BaseState.getDistricts().values()){
                totalVote += sd.getGOPVote();
                totalVote += sd.getDEMVote();
                totalGOPvote += sd.getGOPVote();
                totalDistricts += 1;
                if (sd.getGOPVote() > sd.getDEMVote()) {
                    totalGOPDistricts += 1;
                }
            }
            int idealDistrictChange = ((int) Math.round(totalDistricts * ((1.0 * totalGOPvote) / totalVote))) - totalGOPDistricts;
            // End temporary section
            if (idealDistrictChange == 0) {
                return 1.0;
            }
            int gv = d.getGOPVote();
            int dv = d.getDEMVote();
            int tv = gv + dv;
            int margin = gv - dv;
            if (tv == 0) {
                return 1.0;
            }
            int win_v = Math.max(gv, dv);
            int loss_v = Math.min(gv, dv);
            int inefficient_V;
            if (idealDistrictChange * margin > 0) {
                inefficient_V = win_v - loss_v;
            } else {
                inefficient_V = loss_v;
            }
            return 1.0 - ((inefficient_V * 1.0) / tv);
        }
    },
    REOCK_COMPACTNESS {
        @Override
        public double calculateMeasure(BaseDistrict BaseDistrict) {
            MultiPolygon shape = BaseDistrict.getMulti();
            Geometry boundingCircle = BaseDistrict.getBoundingCircle();
            return shape.getArea() / boundingCircle.getArea();
        }
    },
    CONVEX_HULL_COMPACTNESS {
        @Override
        public double calculateMeasure(BaseDistrict BaseDistrict) {
            MultiPolygon shape = BaseDistrict.getMulti();
            Geometry convexHull = BaseDistrict.getConvexHull();
            return shape.getArea() / convexHull.getArea();
        }
    },
    EDGE_COMPACTNESS {
        /*
            Compactness:
            perimeter / (circle perimeter for same area)
        */
        public double calculateMeasure(BaseDistrict d) {
            double internalEdges = d.getInternalEdges();
            double totalEdges = internalEdges + d.getExternalEdges();
            return internalEdges / totalEdges;
        }
    },
    EFFICIENCY_GAP {
        /**
         * Wasted votes:
         * Statewide: abs(Winning party margin - losing party votes)
         */
        public double calculateMeasure(BaseDistrict d) {
            int iv_g = 0;
            int iv_d = 0;
            int tv = 0;
            BaseState BaseState = d.getState();
            for (BaseDistrict sd : BaseState.getDistricts().values()) {
                int gv = sd.getGOPVote();
                int dv = sd.getDEMVote();
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
        /**
         * Wasted votes:
         * abs(Winning party margin - losing party votes)
         */
        public double rateEfficiencyGap(BaseDistrict d) {//TODO what is this
            int gv = d.getGOPVote();
            int dv = d.getDEMVote();
            int tv = gv + dv;
            if (tv == 0) {
                return 1.0;
            }
            int win_v = Math.max(gv, dv);
            int loss_v = Math.min(gv, dv);
            int inefficient_V = Math.abs(loss_v - (win_v - loss_v));
            return 1.0 - ((inefficient_V * 1.0) / tv);
        }

    },
    POPULATION_EQUALITY {
        public double calculateMeasure(BaseDistrict d) {
            //we will square before we return--this gives lower measure values
            // for greater error
            BaseState BaseState = d.getState();
            int idealPopulation = BaseState.getPopulation() / BaseState.getDistricts().size();
            int truePopulation = d.getPopulation();
            if (idealPopulation >= truePopulation) {
                return 1-Math.pow(
                        Math.abs( idealPopulation-(double)truePopulation)/idealPopulation ,1.25);
            }
            return 1-Math.pow(
                    Math.abs( truePopulation -(double)idealPopulation)
                            /idealPopulation, 1.25);
        }

    },
    COMPETITIVENESS {
        /**
         * COMPETITIVENESS:
         * 1.0 - margin of victory
         */
        public double calculateMeasure(BaseDistrict d) {
            int gv = d.getGOPVote();
            int dv = d.getDEMVote();
            return 1.0 - (((double) Math.abs(gv - dv)) / (gv + dv));
        }
    },
    GERRYMANDER_REPUBLICAN {
        /**
         * GERRYMANDER_REPUBLICAN:
         * Partisan fairness, but always working in the GOP's favor
         */
        public double calculateMeasure(BaseDistrict d) {
            int gv = d.getGOPVote();
            int dv = d.getDEMVote();
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

    },
    POPULATION_HOMOGENEITY {
        /**
         * calculate square error of population, normalized to 0,1
         */
        @Override
        public double calculateMeasure(BaseDistrict BaseDistrict) {
            if (BaseDistrict.getPrecincts().size() == 0)
                return 0;
            double sum = BaseDistrict.getPrecincts().stream().mapToDouble(BasePrecinct::getPopulationDensity).sum();
            final double mean = sum / BaseDistrict.getPrecincts().size();
            double sqError = BaseDistrict.getPrecincts()
                    .stream().mapToDouble(
                            (BasePrecinct) -> (Math.pow(BasePrecinct.getPopulationDensity() - mean, 2))
                    ).sum();
            sqError/=(BaseDistrict.getPrecincts().size());
            //avg population density in km^2
            double averagePopulationDensity = 3000;


            return 1.0 - Math.tanh(Math.sqrt(sqError/mean)/(mean));
        }
    },
    GERRYMANDER_DEMOCRAT {
        /**
         * GERRYMANDER_DEMOCRAT:
         * Partisan fairness, but always working in the DNC's favor
         */
        public double calculateMeasure(BaseDistrict d) {
            int gv = d.getGOPVote();
            int dv = d.getDEMVote();
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
        }
    };
    public abstract double calculateMeasure(BaseDistrict BaseDistrict);
}