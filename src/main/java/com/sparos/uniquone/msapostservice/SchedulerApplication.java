package com.sparos.uniquone.msapostservice;

import com.sparos.uniquone.msapostservice.eco.service.IEcoService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class SchedulerApplication {

    public static void main(String[] args) {
        SpringApplication.run(IEcoService.class, args);
    }

}
