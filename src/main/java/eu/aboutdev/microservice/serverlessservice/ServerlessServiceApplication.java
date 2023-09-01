package eu.aboutdev.microservice.serverlessservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class ServerlessServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(ServerlessServiceApplication.class, args);
    }

}
