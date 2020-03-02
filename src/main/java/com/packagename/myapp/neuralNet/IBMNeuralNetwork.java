

public class IBMNeuralNetwork {
    Neuron[] firstlayer=new Neuron[3];
    Neuron[] secondlayer=new Neuron[1];
    public IBMNeuralNetwork(){
        double[] GB= {0.1,0.1,0.1,0.1,0.1,0.5};
        double[] GR= {0.1,0.1,0.000001,0.1,0.1,0.5};
        double[] WB= {0.1,0.1,0.1,1.0,1.0,0.5};
        double[] last ={1.0,1.0,1.0,0.5};
        firstlayer[0]=new Neuron(GB);
        firstlayer[1]=new Neuron(GR);
        firstlayer[2]=new Neuron(WB);
        secondlayer[0]=new Neuron(last);
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
        if(match<0.4){
            judgement="bad";
        }
        else if( match<0.8){
            judgement="nice";
        }
        else{
            judgement="amazing";
        }
        return judgement;
    }
    public void backpropagation(double desired, double[] inputs, double learning_rate){
        double []modifinput=new double[inputs.length];
        double []changeweight2=new double[secondlayer[0].weights.length];
        double []changeweight1=new double[firstlayer[0].weights.length];
        //gets the inputs for the second layer to begin error propagation
        for(int i=0;i<(inputs.length-1);i++){
            modifinput[i]=firstlayer[i].run(inputs);
        }
        modifinput[inputs.length-1]=1;
        //set changes in weight for the second layer
        double pre_delta = (desired-(run(inputs)))*secondlayer[0].derivative_sigmoid(secondlayer[0].sum(modifinput));
        for(int j=0;j<changeweight2.length;j++) {
            changeweight2[j] = learning_rate * pre_delta * modifinput[j];
        }
        secondlayer[0].update_weights(changeweight2);
        //set changes in weight for the first layer
        for(int i=0;i<firstlayer.length;i++){
            double delta=firstlayer[i].derivative_sigmoid(firstlayer[i].sum(inputs))*pre_delta*secondlayer[0].weights[i];
            for(int j=0;j<inputs.length;j++){
                changeweight1[j]=learning_rate*delta*inputs[j];
            }
            firstlayer[i].update_weights(changeweight1);
        }
    }
}
