package com.packagename.myapp.NeuralNet;

import com.packagename.myapp.NeuralNet.Functions.*;

import java.util.ArrayList;
import java.util.List;

public class NeuralNetwork {
    private List<Layer> layers;
    private TransformFunction transformFunction;
    private CostFunction costFunction;

    private float learningRate;

    public NeuralNetwork(){
        layers = new ArrayList<Layer>();
        transformFunction = new SigmoidTransformFunction();
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
            layers.get(i).updateActivations(transformFunction);
        }
        return layers.get(layers.size()-1).getActivations();
    }

    public void fit(float[] target, float[] input){
        float[] actual = score(input);
        float[] sigmas = layers.get(layers.size()-1).getSigmas();
        float[] errors = new float[target.length];
        for(int i=0;i<errors.length;i++){
            errors[i] = costFunction.df(target[i],actual[i])*transformFunction.df(sigmas[i]);
            layers.get(layers.size()-1).setDeltas(errors);
        }
        for(int i=layers.size()-1;i>=1;i--){
            layers.get(i).updateDeltas(transformFunction);
        }
        for(int i=layers.size()-1;i>=1;i--){
            layers.get(i).updateWeights(learningRate);
        }
    }

    public void addLayer(Layer layer){
        layers.add(layer);
    }

    public void setTransformFunction(TransformFunction transformFunction){
        this.transformFunction = transformFunction;
    }

    public void setCostFunction(CostFunction costFunction){
        this.costFunction = costFunction;
    }

    public void setLearningRate(float learningRate){
        this.learningRate = learningRate;
    }
}
