package com.packagename.myapp.NeuralNet;

class NeuronInput {
    Neuron from;
    float weight;

    NeuronInput(Neuron from, float weight){
        this.from = from;
        this.weight = weight;
    }
}
