package com.packagename.myapp;

import armdb.SQLQueryException;
import com.packagename.myapp.NNDataGenerator.OutfitTestGenerator;
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
        KeyHolder.loadKeys();

        Scanner sc = new Scanner(System.in);
        System.out.println("Type A to start the server, B to populate TestData!");
        if(sc.nextLine().toUpperCase().equals("B")){
            OutfitTestGenerator g = new OutfitTestGenerator();
            System.out.println("Type path to the file with photos!");
            System.out.println("Type file index you wish to start at, if this is the first go type 0. (This is used if there was an error in processing files so " +
                    "you didn't have to start from the first pic again.");
            int i = Integer.parseInt(sc.nextLine());
            g.RunGenerator(sc.nextLine(), i);
            g.PopulateTestArrays();
            System.out.println("Generator is populated");
        }else{
            SpringApplication.run(Application.class, args);
        }
    }

}
