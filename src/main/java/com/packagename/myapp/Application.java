package com.packagename.myapp;

import Database.DatabaseFunctions;
import armdb.SQLQueryException;
import com.jcraft.jsch.JSchException;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

import javax.xml.crypto.Data;
import java.sql.SQLException;

/**
 * The entry point of the Spring Boot application.
 */
@SpringBootApplication
public class Application extends SpringBootServletInitializer {

    public static void main(String[] args) throws SQLException, JSchException, SQLQueryException {
        System.out.println("Database Connecting...");
        DatabaseFunctions.Connect();
        System.out.println("Database Connected!");
        SpringApplication.run(Application.class, args);
    }

}
