package com.example.cowinvaccineavailablityslotapplication;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.scheduling.annotation.EnableScheduling;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@EnableScheduling
@EnableSwagger2
public class CowinVaccineAvailablitySlotApplication extends SpringBootServletInitializer {

    public static void main(String[] args) {
        SpringApplication.run(CowinVaccineAvailablitySlotApplication.class, args);
    }

}
