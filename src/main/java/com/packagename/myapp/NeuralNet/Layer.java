package com.packagename.myapp.NeuralNet;

import com.packagename.myapp.NeuralNet.Functions.TransformFunction;

import java.util.ArrayList;
import java.util.List;

public class Layer {
    List<Neuron> neurons;

    public Layer(int numberOfNeurons){
        neurons = new ArrayList<Neuron>(numberOfNeurons);
        for(int i=0;i<numberOfNeurons;i++){
            neurons.add(new Neuron(0f));
        }
    }

    protected void makeConnections(Layer fromLayer){
        for(int i=0;i<neurons.size();i++){
            Neuron current = neurons.get(i);
            for(int j=0;j<fromLayer.neurons.size();j++){
                Neuron other = fromLayer.neurons.get(j);
                current.connections.add(new Connection(other,.5f));
            }
        }
    }

    void setActivations(float[] activations){
        for(int i=0;i<neurons.size();i++){
            neurons.get(i).activation = activations[i];
        }
    }

    void updateActivations(TransformFunction transformFunction){
        for(Neuron n : neurons){
            for(Connection conn: n.connections){
                n.sigma += conn.weight*conn.from.activation;
            }
            n.sigma += n.bias;
            n.activation = transformFunction.f(n.sigma);
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

    void updateDeltas(TransformFunction transformFunction) {
        for(Neuron n: neurons) {
            for (Connection conn : n.connections) {
                conn.from.delta = 0;
            }
        }
        for(Neuron n: neurons){
            for(Connection conn : n.connections){
                conn.from.delta += n.delta*conn.weight*transformFunction.df(conn.from.sigma);
            }
        }
    }

    void updateWeights(float learningRate) {
        for(Neuron n : neurons){
            for(Connection conn : n.connections){
                conn.weight -= learningRate*n.delta*conn.from.activation;
            }
        }
    }
}
