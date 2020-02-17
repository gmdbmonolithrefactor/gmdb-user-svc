package com.galvanize.gmdb;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class GmdbUserApplication {

    public static void main(String[] args) {
        SpringApplication.run(GmdbUserApplication.class, args);
    }

}
