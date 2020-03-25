package com.packagename.myapp.NeuralNet;

import com.packagename.myapp.NeuralNet.Functions.*;

import java.util.ArrayList;
import java.util.List;

public class NeuralNetwork {
    private Layer[] layers;
    private CostFunction costFunction;
    private float learningRate;

    private NeuralNetwork(Builder builder){
        layers = builder.layers.toArray(new Layer[0]);
        costFunction = builder.costFunction;
        learningRate = builder.learningRate;
    }

    public final float[] evaluate(float[] input){
        layers[0].setActivations(input);
        for(int i=1;i<layers.length;i++){
            layers[i].updateActivations();
        }
        return layers[layers.length-1].getActivations();
    }

    public final void train(float[] target, float[] input){
        evaluate(input);
        layers[layers.length-1].calculateDeltas(target,costFunction);
        for(int i=layers.length-1;i>=1;i--){
            layers[i].updateDeltas();
        }
        for(int i=layers.length-1;i>=1;i--){
            layers[i].updateWeights(learningRate);
        }
    }

    public final float[][][] getWeights(){
        float[][][] weights = new float[layers.length-1][][];
        for(int i=0;i<layers.length-1;i++){
            weights[i] = layers[i+1].getWeights();
        }
        return weights;
    }

    public final float[][] getBiases(){
        float[][] biases = new float[layers.length-1][];
        for(int i=0;i<layers.length-1;i++){
            biases[i] = layers[i+1].getBiases();
        }
        return biases;
    }

    public static class Builder {
        private List<Layer> layers;
        private CostFunction costFunction;
        private TransformFunction defaultTransformFunction;
        private float learningRate;
        private float[][][] initialWeights;
        private float[][] initialBiases;
        private int numberOfInputs;

        public Builder(){
            layers = new ArrayList<Layer>();
            costFunction = new RSSCostFunction();
            defaultTransformFunction = new SigmoidTransformFunction();
            learningRate = .1f;
            initialWeights = null;
            initialBiases = null;
            numberOfInputs = 0;
        }

        public final NeuralNetwork build(){
            if(layers.size() < 2) throw new Error("Need at least two layers!");
            if(numberOfInputs <= 0) throw new Error("Number of inputs must be greater than 0!");
            layers.add(0,new Layer(numberOfInputs));
            for(int i=1;i<layers.size();i++){
                Layer layer = layers.get(i);
                if(layer.transformFunction == null){
                    layer.transformFunction = defaultTransformFunction;
                }
                layers.get(i).setupNeurons(layers.get(i-1),
                        (initialWeights!=null)?initialWeights[i-1]:null,
                        (initialBiases!=null)?initialBiases[i-1]:null);
            }
            return new NeuralNetwork(this);
        }

        public final Builder setNumberOfInputs(int numberOfInputs){
            this.numberOfInputs = numberOfInputs;
            return this;
        }

        public final Builder addLayer(Layer layer){
            layers.add(layer);
            return this;
        }

        public final Builder setCostFunction(CostFunction costFunction){
            this.costFunction = costFunction;
            return this;
        }

        public final Builder setDefaultTransformFunction(TransformFunction defaultTransformFunction){
            this.defaultTransformFunction = defaultTransformFunction;
            return this;
        }

        public final Builder setLearningRate(float newLearningRate){
            this.learningRate = newLearningRate;
            return this;
        }

        public final Builder setInitialWeights(float[][][] initialWeights){
            this.initialWeights = initialWeights;
            return this;
        }

        public final Builder setInitialBiases(float[][] initialBiases){
            this.initialBiases = initialBiases;
            return this;
        }
    }
}
