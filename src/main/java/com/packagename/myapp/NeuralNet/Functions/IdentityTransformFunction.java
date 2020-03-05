package com.packagename.myapp.NeuralNet.Functions;

public class IdentityTransformFunction extends TransformFunction {
    @Override
    public float f(float x) {
        return x;
    }

    @Override
    public float df(float x) {
        return 1;
    }
}
