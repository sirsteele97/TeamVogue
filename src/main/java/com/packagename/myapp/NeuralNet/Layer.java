package com.packagename.myapp.NeuralNet;

import com.packagename.myapp.NeuralNet.Functions.CostFunction;
import com.packagename.myapp.NeuralNet.Functions.SigmoidTransformFunction;
import com.packagename.myapp.NeuralNet.Functions.TransformFunction;

import java.util.ArrayList;
import java.util.List;

public class Layer {

    List<Neuron> neurons;

    private TransformFunction transformFunction;

    public Layer(int numberOfNeurons){
        transformFunction = new SigmoidTransformFunction();

        neurons = new ArrayList<Neuron>(numberOfNeurons);
        for(int i=0;i<numberOfNeurons;i++){
            neurons.add(new Neuron(0f));
        }
    }

    public Layer setTransformationFunction(TransformFunction transformFunction){
        this.transformFunction = transformFunction;
        return this;
    }

    protected void makeConnections(Layer fromLayer){
        for(int i=0;i<neurons.size();i++){
            Neuron current = neurons.get(i);
            for(int j=0;j<fromLayer.neurons.size();j++){
                Neuron other = fromLayer.neurons.get(j);
                current.connections.add(new Connection(other,(float)(Math.random()*2)-1));
            }
        }
    }

    void setActivations(float[] activations){
        for(int i=0;i<neurons.size();i++){
            neurons.get(i).activation = activations[i];
        }
    }

    void updateActivations(){
        for(Neuron n : neurons){
            n.sigma = 0;
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

    void calculateDeltas(float[] target, CostFunction costFunction){
        for(int i=0;i<target.length;i++){
            neurons.get(i).delta = costFunction.df(target[i],neurons.get(i).activation);
        }
    }

    void updateDeltas() {
        for(Neuron n: neurons){
            for(Connection conn : n.connections){
                conn.from.delta += n.delta*conn.weight;
            }
        }
    }

    void updateWeights(float learningRate) {
        for(Neuron n : neurons){
            n.delta *= transformFunction.df(n.sigma);
            n.bias -= learningRate*n.delta;
            for(Connection conn : n.connections){
                conn.weight -= learningRate*n.delta*conn.from.activation;
            }
            n.delta = 0;
        }
    }
}
