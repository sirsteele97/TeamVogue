package com.packagename.myapp.NeuralNet;

class Connection {
    Neuron to;
    float weight;

    Connection(Neuron to, float weight){
        this.to = to;
        this.weight = weight;
    }
}
