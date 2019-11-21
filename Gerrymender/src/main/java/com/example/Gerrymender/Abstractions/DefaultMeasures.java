package com.example.Gerrymender.Abstractions;

import com.example.Gerrymender.Abstractions.AbstrInterface.MeasureFunction;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;

public class DefaultMeasures implements MeasureFunction<BasePrecinct, BaseDistrict> {

    // a map that gives a function to generate the "weights" for a given measure, usually just a constant function f(BaseDistrict) = C.
    private Map<Measure, Function<BaseDistrict, Double>> weightGenerator;

    // an activation function that is run, piecewise on each measure cost, before the weight is applied.
    // this is typically x-> 1-(1-x)^2, in accordance with Matt's original code.
    private Function<Double,Double> defaultActivationFunction;


    // creates a DefaultMeasures object with the given set of Measure,Weight tuples and vanilla activation function.
    public static DefaultMeasures defaultMeasuresWithWeights(Map<Measure, Double> weights) {
        DefaultMeasures newMeasureSet = new DefaultMeasures(new HashMap<>());
        newMeasureSet.updateConstantWeights(weights);
        return newMeasureSet;
    }
    public void updateConstantWeights(Map<Measure,Double> weights) {
        this.weightGenerator = new HashMap<>();
        for (Measure measure : weights.keySet()) {
            this.weightGenerator.put(measure, (BaseDistrict) -> weights.get(measure));
        }
    }

    public DefaultMeasures(Map<Measure, Function<BaseDistrict, Double>> weightGenerator,
                           Function<Double, Double> defaultActivationFunction) {
        this.weightGenerator = weightGenerator;
        this.defaultActivationFunction = defaultActivationFunction;
    }
    public DefaultMeasures(Map<Measure, Function<BaseDistrict, Double>> weightedActivation) {
        this(weightedActivation, (x) -> 1 - Math.pow((1 - x), 2));
    }


    @Override
    public double calculateMeasure(BaseDistrict BaseDistrict) {
        double sum = 0;
        for (Map.Entry<Measure, Function<BaseDistrict,Double>> entry: weightGenerator.entrySet()) {
            Measure measure = entry.getKey();
            //the weight is applied as a seperate factor
            double weight = entry.getValue().apply(BaseDistrict);
            //calculate the measure, run it through the activation function, multiply it by the weight.
            sum += defaultActivationFunction.apply(measure.calculateMeasure(BaseDistrict)) *
                    weight;
        }
        return sum;
    }


    @Override
    public Set<MeasureFunction<BasePrecinct, BaseDistrict>> subMeasures() {
        return (Set) weightGenerator.keySet();
    }
}
