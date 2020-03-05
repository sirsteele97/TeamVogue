package com.packagename.myapp.NeuralNet.Functions;

public class RSSCostFunction extends CostFunction {
    @Override
    public float df(float target, float actual) {
        return -(target-actual);
    }
}
