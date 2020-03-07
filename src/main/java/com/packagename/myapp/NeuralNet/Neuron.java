package com.packagename.myapp.NeuralNet;

import java.util.LinkedList;
import java.util.List;

class Neuron {
    float sigma;
    float activation;
    float delta;
    float bias;

    List<Input> inputs;

    Neuron(){
        sigma = 0;
        activation = 0;
        delta = 0;
        this.bias = bias;

        inputs = new LinkedList<Input>();
    }
}
