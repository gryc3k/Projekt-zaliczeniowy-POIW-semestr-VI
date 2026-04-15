package com.mycompany.projektroboty;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class KosiarkobotApplication {
    public static void main(String[] args) {
        SpringApplication.run(KosiarkobotApplication.class, args);
    }

    // Ten kod uruchomi się automatycznie tuż po starcie serwera
    @Bean
    public CommandLineRunner initData(RobotRepository repository) {
        return args -> {
            // Dodajemy roboty tylko wtedy, gdy baza jest pusta
            if (repository.count() == 0) {
                repository.save(new Robot("Husqvarna", "Automower 305", "Koszący", 600, 5599.0, 2));
                repository.save(new Robot("Worx", "Landroid M500", "Koszący", 500, 2999.0, 5));
                System.out.println("--- BAZA DANYCH ZAINICJALIZOWANA ---");
            }
        };
    }
}