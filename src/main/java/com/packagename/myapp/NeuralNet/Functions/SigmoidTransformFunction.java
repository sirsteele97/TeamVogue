package com.packagename.myapp.NeuralNet.Functions;

public class SigmoidTransformFunction extends TransformFunction {
    @Override
    public float f(float x) {
        return (float)(1f/(1f+Math.exp(-x)));
    }

    @Override
    public float df(float x) {
        return f(x)*(1-f(x));
    }
}
