package com.packagename.myapp.neuralNet;

public class ClassifierNet {
    public final double LEARNING_RATE = .01;

    NeuronCls[] firstlayer=new NeuronCls[3];
    NeuronCls[] secondlayer=new NeuronCls[1];

    //{solid, stripe, dot, plaid, icon}
    double[] patternWeights = {0.01, 0.01, 0.01, 0.01, 0.01};

    public ClassifierNet(){
        //{red, green, blue, white, black, 0.5}
        double[] GB= {0.01,1.0,1.0,0.01,0.01,0.5};
        double[] GR= {2.0,2.0,0.01,0.01,0.01,0.5};
        double[] WB= {0.01,0.01,0.01,1.0,1.0,0.5};

        //last, not sure what its function is
        double[] last ={1.0,-1.0,2.0,1.0,0.2};

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
    }
}
