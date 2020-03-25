package com.packagename.myapp;

import armdb.SQLQueryException;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.packagename.myapp.NeuralNet.Functions.IdentityTransformFunction;
import com.packagename.myapp.NeuralNet.Functions.SigmoidTransformFunction;
import com.packagename.myapp.NeuralNet.Layer;
import com.packagename.myapp.NeuralNet.NeuralNetwork;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

import java.sql.SQLException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

/**
 * The entry point of the Spring Boot application.
 */
@SpringBootApplication
public class Application extends SpringBootServletInitializer {

    public static void main(String[] args) throws SQLException, SQLQueryException {
        String initialWeights = "[[[-7.2749615, -6.385663], [2.587251, 4.445902], [-5.4033694, 4.261566], [-1.810219, -4.178541]], [[-12.614047, -7.63313, 6.3037295, 6.5746684]]]";
        String initialBiases = "[[3.025201, -5.293949, -2.6812415, 4.4179044], [-0.2569598]]";

        Gson gson = new Gson();
        float[][][] initialWeightsArray = gson.fromJson(initialWeights,float[][][].class);
        float[][] initialBiasesArray = gson.fromJson(initialBiases,float[][].class);

        NeuralNetwork network = new NeuralNetwork.Builder()
                .setNumberOfInputs(2)
                .addLayer(new Layer(4))
                .addLayer(new Layer(1))
                .setDefaultTransformFunction(new SigmoidTransformFunction())
                .setInitialWeights(initialWeightsArray)
                .setInitialBiases(initialBiasesArray)
                .setLearningRate(.1f)
                .build();

        /*float[] trainingA = new float[10000000];
        float[] trainingB = new float[trainingA.length];


        Random r = new Random();
        for(int i=0;i<trainingA.length;i++){
            trainingA[i] = r.nextInt(2);
            trainingB[i] = r.nextInt(2);
            //System.out.println(trainingA[i]+" xor "+trainingB[i]+" = "+target[i]);
        }

        for(int i=0;i<trainingA.length;i++){
            float a=trainingA[i];
            float b=trainingB[i];
            float out = a+b;
            if(a==b) out = 0;
            else out = 1;
            network.train(new float[]{out},new float[]{a,b});
        }*/

        float result = network.evaluate(new float[]{0f,0f})[0];
        System.out.println(result);
        result = network.evaluate(new float[]{1,0})[0];
        System.out.println(result);
        result = network.evaluate(new float[]{0f,1f})[0];
        System.out.println(result);
        result = network.evaluate(new float[]{1,1})[0];
        System.out.println(result);

        float[][][] weights = network.getWeights();
        System.out.println(Arrays.deepToString(weights));
        float[][] biases = network.getBiases();
        System.out.println(Arrays.deepToString(biases));

        //KeyHolder.loadKeys();
        //SpringApplication.run(Application.class, args);
    }

}
