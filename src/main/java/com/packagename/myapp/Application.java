package com.packagename.myapp;

import armdb.SQLQueryException;
import com.packagename.myapp.NNDataGenerator.OutfitTestGenerator;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

import java.sql.SQLException;
import java.util.Scanner;

/**
 * The entry point of the Spring Boot application.
 */
@SpringBootApplication
public class Application extends SpringBootServletInitializer {

    public static void main(String[] args) throws SQLException, SQLQueryException {
        //KeyHolder.loadKeys();


        OutfitTestGenerator g = new OutfitTestGenerator();
        Scanner sc = new Scanner(System.in);
        System.out.println("Type A to start the server, B to populate TestData!");
        String s = sc.nextLine();
        if(s.toUpperCase().equals("B")){
            System.out.println("Type path to the file with photos!");
            s = sc.nextLine();
            g.RunGenerator(s);
            System.out.println("Generator is populated");
        }else{
            SpringApplication.run(Application.class, args);
        }

    }

}
