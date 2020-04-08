package com.packagename.myapp.neuralNet;

import com.google.gson.Gson;

import java.io.*;

public class ClassifierNet {
    public final double LEARNING_RATE = .01;

    NeuronCls[] firstlayer=new NeuronCls[3];
    NeuronCls[] secondlayer=new NeuronCls[1];

    //{solid, stripe, dot, plaid, icon}
    double[] patternWeights = {0.01, 0.01, 0.01, 0.01, 0.01, 0.5};

    public ClassifierNet(){
        Weights initialWeights = instantiateWeights();
        //{red, green, blue, white, black, 0.5}
        double[] GB= initialWeights.GB;
        double[] GR= initialWeights.GR;
        double[] WB= initialWeights.WB;

        //last, not sure what its function is
        double[] last = initialWeights.last;
        patternWeights = initialWeights.patternWeights;

        firstlayer[0]=new NeuronCls(GB);
        firstlayer[1]=new NeuronCls(GR);
        firstlayer[2]=new NeuronCls(WB);
        secondlayer[0] = new NeuronCls(last);
    }

    public double run(double[] colors){
        double[] afterInput=new double[3];
        for(int i=0;i<3;i++){
            afterInput[i]=firstlayer[i].run(colors);
        }

        return secondlayer[0].run(afterInput);
    }
    public String judge(double[] colors, double[] patterns){
        String judgement;

        //get color value
        double match = run(colors);

        //get pattern value
        for(int i = 0; i < patterns.length; i++){
            match += patterns[i] * patternWeights[i];
        }

        //output depending on model values
        if(match<0){
            judgement="bad";
        }
        else if(match<3){
            judgement="nice";
        }
        else{
            judgement="amazing";
        }
        return judgement;
    }
    public void backpropagation(String desired, double[][] datapoint, double learning_rate){
        double[] inputs = datapoint[0];
        if(desired.equals("good")){
            for(int i=0;i<firstlayer.length;i++){
                if(secondlayer[0].weights[i]>0){
                    for(int j=0;j<firstlayer[i].weights.length-1;j++){
                        firstlayer[i].weights[j]=firstlayer[i].weights[j]+learning_rate*inputs[j];
                    }
                }
                else{
                    for(int j=0;j<firstlayer[i].weights.length-1;j++){
                        firstlayer[i].weights[j]=firstlayer[i].weights[j]-learning_rate*inputs[j];
                    }
                }
            }
        }
        else if (desired.equals("bad")){
            for(int i=0;i<firstlayer.length;i++){
                if(secondlayer[0].weights[i]>0){
                    for(int j=0;j<firstlayer[i].weights.length-1;j++){
                        firstlayer[i].weights[j]=firstlayer[i].weights[j]-learning_rate*inputs[j];
                    }
                }
                else{
                    for(int j=0;j<firstlayer[i].weights.length-1;j++){
                        firstlayer[i].weights[j]=firstlayer[i].weights[j]+learning_rate*inputs[j];
                    }
                }
            }

        }

        double[] patterns = datapoint[1];
        if(desired.equals("good")) {
            for(int i=0; i<patterns.length;i++){
                patternWeights[i] += patterns[i] * learning_rate;
            }
        } else if(desired.equals("bad")){
            for(int i=0; i<patterns.length;i++){
                patternWeights[i] -= patterns[i] * learning_rate;
            }
        }

        Weights updatedWeights = new Weights();
        updatedWeights.GB = firstlayer[0].weights;
        updatedWeights.GR = firstlayer[1].weights;
        updatedWeights.WB = firstlayer[2].weights;
        updatedWeights.last = secondlayer[0].weights;
        updatedWeights.patternWeights = patternWeights;
        storeWeights(updatedWeights);
    }

    /**
     * JSON helper methods and class.
     */
    private Weights instantiateWeights() {
        String filename = "src/main/java/com/packagename/myapp/neuralNet/trained_weights.json";
        File temp = new File(filename);
        Weights w = new Weights();
        if(temp.exists()){
            try(Reader reader = new FileReader(filename)){
                Gson g = new Gson();
                w = g.fromJson(reader,Weights.class);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return w;
    }

    private void storeWeights(Weights updatedWeights){
        try (Writer writer = new FileWriter("src/main/java/com/packagename/myapp/neuralNet/trained_weights.json")){
            Gson gson = new Gson();
            gson.toJson(updatedWeights,Weights.class,writer);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
