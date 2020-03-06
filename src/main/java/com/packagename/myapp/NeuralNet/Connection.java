package com.packagename.myapp.NeuralNet;

class Connection {
    Neuron from;
    float weight;

    Connection(Neuron from, float weight){
        this.from = from;
        this.weight = weight;
    }
}
