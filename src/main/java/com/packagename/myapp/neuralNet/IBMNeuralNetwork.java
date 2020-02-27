

public class IBMNeuralNetwork {
    Neuron[] n=new Neuron[4];
    public IBMNeuralNetwork(){
        double[] GB= {0.1,0.1,0.1,0.1,0.1,0.5};
        double[] GR= {0.1,0.1,0.000001,0.1,0.1,0.5};
        double[] WB= {0.1,0.1,0.1,1.0,1.0,0.5};
        double[] last ={1.0,1.0,1.0,0.5};
        n[0]=new Neuron(GB);
        n[1]=new Neuron(GR);
        n[2]=new Neuron(WB);
        n[3]=new Neuron(last);
    }

    public double run(double[] colors){
        double[] afterInput=new double[3];
        for(int i=0;i<3;i++){
            afterInput[i]=n[i].run(colors);
        }

        return n[3].run(afterInput);
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
    public void backpropagation(){

    }
}
