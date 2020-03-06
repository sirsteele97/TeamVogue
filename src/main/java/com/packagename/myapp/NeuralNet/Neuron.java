package com.packagename.myapp.NeuralNet;

import java.util.LinkedList;
import java.util.List;

class Neuron {
    float sigma;
    float activation;
    float delta;
    float bias;

    List<Connection> connections;

    Neuron(float bias){
        sigma = 0;
        activation = 0;
        delta = 0;
        this.bias = bias;

        connections = new LinkedList<Connection>();
    }
}
