package dev.shared.resources.grpc;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SpringCamelGrpcApplication {

    public static void main(String[] args) throws InterruptedException {
        SpringApplication.run(SpringCamelGrpcApplication.class, args);

        synchronized (SpringCamelGrpcApplication.class) {
            SpringCamelGrpcApplication.class.wait();
        }
    }
}
