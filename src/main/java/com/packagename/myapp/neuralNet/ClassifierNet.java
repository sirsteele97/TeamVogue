package com.packagename.myapp.neuralNet;

public class ClassifierNet {
    NeuronCls[] firstlayer=new NeuronCls[3];
    NeuronCls[] secondlayer=new NeuronCls[1];
    int inputNum=6;
    public ClassifierNet(){
        double[] GB= {0.01,1.0,1.0,0.01,0.01,0.5};
        double[] GR= {2.0,2.0,0.01,0.01,0.01,0.5};
        double[] WB= {0.01,0.01,0.01,1.0,1.0,0.5};
        double[] last ={1.0,-1.0,2.0,0.2};
        firstlayer[0]=new NeuronCls(GB);
        firstlayer[1]=new NeuronCls(GR);
        firstlayer[2]=new NeuronCls(WB);
        secondlayer[0]=new NeuronCls(last);
    }

    public double run(double[] colors){
        double[] afterInput=new double[3];
        for(int i=0;i<3;i++){
            afterInput[i]=firstlayer[i].run(colors);
        }

        return secondlayer[0].run(afterInput);
    }
    public String judge(double[] colors){
        String judgement;
        double match=run(colors);
        System.out.println(match);
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
    public void backpropagation(String desired, double[] inputs, double learning_rate){
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
        else{
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

    }
}
