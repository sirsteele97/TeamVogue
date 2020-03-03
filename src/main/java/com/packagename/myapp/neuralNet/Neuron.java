package com.packagename.myapp.neuralNet;

import java.lang.*;
public class Neuron {
    double[] weights;
    public Neuron(double [] w){
        weights=w;
    }

    public static double sigmoid(double x){
        return 1/(1+Math.exp(-x));
    }
    public double derivative_sigmoid(double x){
        return sigmoid(x)*(1-sigmoid(x));
    }
    public double run(double[] inputs){
        
        double sum=0.0;
        for(int i=0;i<inputs.length;i++){
            sum=sum+weights[i]*inputs[i];
        }
        double output=sigmoid(sum);

        return output;
    }


}
