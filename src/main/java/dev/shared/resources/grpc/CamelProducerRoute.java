package dev.shared.resources.grpc;

import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;


@Component
public class CamelProducerRoute extends RouteBuilder {
    @Override
    public void configure() throws Exception {
        from("timer://grpc-timer?period=10000")
                .log("Timer Started")// Use a timer to trigger the gRPC request every 10 seconds
                .setBody(constant(BanksService.Empty.getDefaultInstance())) // Set the body to an Empty request object
                .to("grpc://localhost:50051/dev.shared.resources.grpc.AccountService?method=GetAllAccounts&synchronous=true")
                .split(body()) // The response is a stream, so we split it into individual Account objects
                .log("Received account details: ${body}")
                .process(exchange -> {
                    // Extract account details from the response
                    BanksService.Account account = exchange.getIn().getBody(BanksService.Account.class);
                    if (account != null) {
                        System.out.println("Account ID: " + account.getId());
                        System.out.println("Account Name: " + account.getName());
                        System.out.println("Account Balance: " + account.getBalance());
                    }
                });
    }
}
