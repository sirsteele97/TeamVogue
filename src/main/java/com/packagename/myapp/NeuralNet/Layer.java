package com.packagename.myapp.NeuralNet;

import com.packagename.myapp.NeuralNet.Functions.CostFunction;
import com.packagename.myapp.NeuralNet.Functions.TransformFunction;

public class Layer {
    Neuron[] neurons;
    TransformFunction transformFunction;

    public Layer(int numberOfNeurons){
        neurons = new Neuron[numberOfNeurons];
        for(int i=0;i<numberOfNeurons;i++){
            neurons[i] = new Neuron();
        }
    }

    protected void setupNeurons(Layer fromLayer, float[][] initialWeights, float[] initialBiases){
        for(int i=0;i<neurons.length;i++){
            neurons[i].bias = (initialBiases != null)?initialBiases[i]:0f;
            for(int j=0;j<fromLayer.neurons.length;j++){
                float weight = (initialWeights != null)?initialWeights[i][j]:(float)((Math.random()*(2))-1);
                neurons[i].inputs.add(new NeuronInput(fromLayer.neurons[j],weight));
            }
        }
    }

    void setActivations(float[] activations){
        for(int i=0;i<neurons.length;i++){
            neurons[i].activation = activations[i];
        }
    }

    void updateActivations(){
        for(Neuron n : neurons){
            n.sigma = 0;
            for(NeuronInput conn: n.inputs){
                n.sigma += conn.weight*conn.from.activation;
            }
            n.sigma += n.bias;
            n.activation = transformFunction.f(n.sigma);
        }
    }

    float[] getActivations(){
        float[] activations = new float[neurons.length];
        for(int i=0;i<activations.length;i++){
            activations[i] = neurons[i].activation;
        }
        return activations;
    }

    void calculateDeltas(float[] target, CostFunction costFunction){
        for(int i=0;i<target.length;i++){
            neurons[i].delta = costFunction.df(target[i],neurons[i].activation)*transformFunction.df(neurons[i].sigma);
        }
    }

    void updateDeltas() {
        for(Neuron n: neurons){
            for(NeuronInput conn : n.inputs){
                conn.from.delta += n.delta*conn.weight*transformFunction.df(conn.from.sigma);
            }
        }
    }

    void updateWeights(float learningRate) {
        for(Neuron n : neurons){
            n.bias -= learningRate*n.delta;
            for(NeuronInput conn : n.inputs){
                conn.weight -= learningRate*n.delta*conn.from.activation;
            }
            n.delta = 0;
        }
    }

    void setWeights(float[][] weights) {
        for(int i=0;i<neurons.length;i++){
            Neuron n = neurons[i];
            for(int j=0;j<n.inputs.size();j++){
                n.inputs.get(i).weight = weights[i][j];
            }
        }
    }

    float[][] getWeights(){
        float[][] weights = new float[neurons.length][];
        for(int i=0;i<weights.length;i++){
            Neuron n = neurons[i];
            float[] neuronWeights = new float[n.inputs.size()];
            for(int j=0;j<n.inputs.size();j++){
                neuronWeights[j] = n.inputs.get(j).weight;
            }
            weights[i] = neuronWeights;
        }
        return weights;
    }

    void setBiases(float[] biases){
        for(int i=0;i<neurons.length;i++){
            neurons[i].bias = biases[i];
        }
    }

    float[] getBiases(){
        float[] biases = new float[neurons.length];
        for(int i=0;i<biases.length;i++) {
            Neuron n = neurons[i];
            biases[i] = n.bias;
        }
        return biases;
    }

    public final Layer setTransformFunction(TransformFunction transformFunction){
        this.transformFunction = transformFunction;
        return this;
    }
}
