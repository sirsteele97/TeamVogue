package com.packagename.myapp.NeuralNet;

import com.packagename.myapp.NeuralNet.Functions.TransformFunction;

import java.util.ArrayList;
import java.util.List;

public class Layer {
    List<Neuron> neurons;

    public Layer(int numberOfNeurons){
        neurons = new ArrayList<Neuron>(numberOfNeurons);
        for(int i=0;i<numberOfNeurons;i++){
            neurons.add(new Neuron());
        }
    }

    protected void makeConnections(Layer fromLayer){
        for(int i=0;i<neurons.size();i++){
            Neuron current = neurons.get(i);
            for(int j=0;j<fromLayer.neurons.size();j++){
                Neuron other = fromLayer.neurons.get(j);
                other.outs.put(i,new Connection(current,(float)(Math.random()*2)-1f));
                current.ins.put(j,other);
            }
        }
    }

    void setActivations(float[] activations){
        for(int i=0;i<neurons.size();i++){
            neurons.get(i).activation = activations[i];
        }
    }

    void updateActivations(TransformFunction transformFunction){
        for(int i=0;i<neurons.size();i++){
            Neuron toUpdate = neurons.get(i);
            toUpdate.sigma = 0;
            for(Neuron n: toUpdate.ins.values()){
                toUpdate.sigma += n.outs.get(i).weight*n.activation;
            }
            toUpdate.activation = transformFunction.f(toUpdate.sigma);
        }
    }

    float[] getActivations(){
        float[] activations = new float[neurons.size()];
        for(int i=0;i<activations.length;i++){
            activations[i] = neurons.get(i).activation;
        }
        return activations;
    }

    float[] getSigmas() {
        float[] sigmas = new float[neurons.size()];
        for(int i=0;i<sigmas.length;i++){
            sigmas[i] = neurons.get(i).sigma;
        }
        return sigmas;
    }

    void setDeltas(float[] deltas){
        for(int i=0;i<neurons.size();i++){
            neurons.get(i).delta = deltas[i];
        }
    }

    void updateDeltas() {
        for(Neuron n: neurons){
            float sum = 0;
            for(Connection conn : n.outs.values()){
                sum += conn.to.delta * conn.weight;
            }
        }
    }

    void updateWeights(float learningRate) {
        for(Neuron n : neurons){
            for(Connection conn : n.outs.values()){
                conn.weight -= learningRate * conn.to.delta * n.activation;
            }
        }
    }
}
