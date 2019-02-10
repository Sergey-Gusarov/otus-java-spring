package ru.sergey_gusarov.hw25;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.integration.annotation.IntegrationComponentScan;
import org.springframework.integration.channel.DirectChannel;
import org.springframework.integration.channel.QueueChannel;
import org.springframework.integration.dsl.channel.MessageChannels;
import org.springframework.messaging.PollableChannel;
import org.springframework.messaging.support.MessageBuilder;

@SpringBootApplication
@IntegrationComponentScan
public class App {

    public static void main(String[] args) throws InterruptedException {
        ConfigurableApplicationContext ctx = SpringApplication.run(App.class, args);

        PollableChannel channel1 = ctx.getBean("channel1", PollableChannel.class);
        DirectChannel channel2 = ctx.getBean("channel2", DirectChannel.class);

        //channel1.subscribe((message) -> channel2.send(message));
        channel2.subscribe(message -> System.out.println(message));

        new Thread(() -> {
            while (true) {
                channel2.send(channel1.receive());
            }
        }).start();

        channel1.send(MessageBuilder.withPayload("Hello").build());
        channel1.send(MessageBuilder.withPayload("Hello2").build());


        Thread.sleep(200);

        channel1.send(MessageBuilder.withPayload("Hello3").build());

        Thread.sleep(1000);
    }

    @Bean
    public PollableChannel channel1() {
        return new QueueChannel(100);
    }

    @Bean
    public DirectChannel channel2() {
        return MessageChannels.direct("channel2").get();
    }
}
