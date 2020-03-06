package com.packagename.myapp.NeuralNet;

import com.packagename.myapp.NeuralNet.Functions.*;

import java.util.ArrayList;
import java.util.List;

public class NeuralNetwork {
    private List<Layer> layers;
    private CostFunction costFunction;

    private float learningRate;

    public NeuralNetwork(){
        layers = new ArrayList<Layer>();
        costFunction = new RSSCostFunction();
        learningRate = .1f;
    }

    public void init(){
        for(int i=1;i<layers.size();i++){
            layers.get(i).makeConnections(layers.get(i-1));
        }
    }

    public float[] score(float[] input){
        layers.get(0).setActivations(input);
        for(int i=1;i<layers.size();i++){
            layers.get(i).updateActivations();
        }
        return layers.get(layers.size()-1).getActivations();
    }

    public void fit(float[] target, float[] input){
        layers.get(layers.size()-1).calculateDeltas(target,costFunction);
        for(int i=layers.size()-1;i>=1;i--){
            layers.get(i).updateDeltas();
        }
        for(int i=layers.size()-1;i>=1;i--){
            layers.get(i).updateWeights(learningRate);
        }
    }

    public void addLayer(Layer layer){
        layers.add(layer);
    }

    public void setCostFunction(CostFunction costFunction){
        this.costFunction = costFunction;
    }

    public void setLearningRate(float learningRate){
        this.learningRate = learningRate;
    }
}
