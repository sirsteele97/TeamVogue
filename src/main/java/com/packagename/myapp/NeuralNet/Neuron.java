package com.packagename.myapp.NeuralNet;

import java.util.HashMap;
import java.util.Map;

class Neuron {
    float sigma;
    float activation;
    float delta;

    Map<Integer,Neuron> ins;
    Map<Integer,Connection> outs;

    Neuron(){
        sigma = 0;
        activation = 0;
        delta = 0;

        ins = new HashMap<Integer,Neuron>();
        outs = new HashMap<Integer,Connection>();
    }
}
