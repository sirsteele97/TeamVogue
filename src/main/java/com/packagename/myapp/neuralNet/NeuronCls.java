package com.packagename.myapp.neuralNet;

public class NeuronCls {
    double[] weights;
    public NeuronCls(double [] w){
        weights=w;
    }


    public double sum(double[] inputs){
        double sum=0.0;
        for(int i=0;i<inputs.length;i++){
            sum=sum+weights[i]*inputs[i];
        }
        return sum;
    }
    public double run(double[] inputs){
        
        double sum=sum(inputs);
        double output=sum;

        return output;
    }
    public void update_weights(double[] add_w){
        for(int i=0;i<weights.length;i++){
            weights[i]=weights[i]+add_w[1];
        }
    }

}
