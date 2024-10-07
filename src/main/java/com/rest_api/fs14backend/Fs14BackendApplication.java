package com.rest_api.fs14backend;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.CommandLineRunner;
import org.springframework.beans.factory.annotation.Autowired;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

@SpringBootApplication
@OpenAPIDefinition(info = @Info(title="Boimela API", version = "1.0.1"))
public class Fs14BackendApplication {
	

	public static void main(String[] args) {
		
		SpringApplication.run(Fs14BackendApplication.class, args);
	}

	/* @Override
    public void run(String... args) throws Exception {
       try (Connection connection = dataSource.getConnection()) {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT 1");
            if (resultSet.next()) {
                System.out.println("Spring Boot connected to PostgreSQL successfully.");
            }
        } catch (Exception e) {
            System.out.println("Failed to connect to PostgreSQL in Spring Boot: " + e.getMessage());
        }
    } */
	

}
