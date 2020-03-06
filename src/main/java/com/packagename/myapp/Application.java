package com.packagename.myapp;

import armdb.SQLQueryException;
import com.packagename.myapp.NeuralNet.Functions.IdentityTransformFunction;
import com.packagename.myapp.NeuralNet.Layer;
import com.packagename.myapp.NeuralNet.NeuralNetwork;
import com.packagename.myapp.Utils.KeyHolder;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

import java.sql.SQLException;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

/**
 * The entry point of the Spring Boot application.
 */
@SpringBootApplication
public class Application extends SpringBootServletInitializer {

    public static void main(String[] args) throws SQLException, SQLQueryException {
        NeuralNetwork network = new NeuralNetwork();
        network.addLayer(new Layer(2));
        network.addLayer(new Layer(1));
        network.setLearningRate(.1f);
        network.setTransformFunction(new IdentityTransformFunction());

        network.init();

        float x=.3f,y=.4f;
        System.out.println(network.score(new float[]{x,y})[0]);

        /*Random r = new Random();
        for(int i=0;i<100;i++){
            float a=(float)Math.random();
            float b=(float)Math.random();
            float out = a+b;
            network.fit(new float[]{out},new float[]{a,b});
        }

        float result = network.score(new float[]{x,y})[0];
        System.out.println(result);*/


        //KeyHolder.loadKeys();
        //SpringApplication.run(Application.class, args);
    }

}
