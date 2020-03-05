package com.packagename.myapp;

import armdb.SQLQueryException;
import com.packagename.myapp.NeuralNet.Layer;
import com.packagename.myapp.NeuralNet.NeuralNetwork;
import com.packagename.myapp.Utils.KeyHolder;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

import java.sql.SQLException;
import java.util.List;
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

        network.init();

        System.out.println(network.score(new float[]{.1f,.1f})[0]);

        for(int i=0;i<1000000;i++){
            float a=(i/100000f)*.5f;
            float b=.5f;
            float sum = a+b;
            network.fit(new float[]{sum},new float[]{a,b});
        }
        System.out.println(network.score(new float[]{.1f,.1f})[0]);


        //KeyHolder.loadKeys();
        //SpringApplication.run(Application.class, args);
    }

}
