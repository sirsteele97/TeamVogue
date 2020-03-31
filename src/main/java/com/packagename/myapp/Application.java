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

            SpringApplication.run(Application.class, args);

    }

}
