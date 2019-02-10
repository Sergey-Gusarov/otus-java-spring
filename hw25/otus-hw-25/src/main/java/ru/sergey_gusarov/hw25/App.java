package ru.sergey_gusarov.hw25;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.apache.commons.lang3.RandomUtils;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.integration.annotation.IntegrationComponentScan;
import org.springframework.integration.channel.DirectChannel;
import org.springframework.integration.channel.PublishSubscribeChannel;
import org.springframework.integration.channel.QueueChannel;
import org.springframework.integration.config.EnableIntegration;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.integration.dsl.IntegrationFlows;
import org.springframework.integration.dsl.channel.MessageChannels;
import org.springframework.integration.scheduling.PollerMetadata;
import org.springframework.messaging.PollableChannel;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.scheduling.support.PeriodicTrigger;
import ru.sergey_gusarov.hw25.domain.Food;
import ru.sergey_gusarov.hw25.domain.OrderItem;

import java.util.ArrayList;
import java.util.List;

@SpringBootApplication
@IntegrationComponentScan
@SuppressWarnings({"resource", "Duplicates", "InfiniteLoopStatement"})
@Configuration
@EnableIntegration
public class App {
    private static final String[] MENU = {"coffee", "tea", "smoothie", "whiskey", "beer", "cola", "water"};

    public static void main(String[] args) throws Exception {
        AbstractApplicationContext ctx = new AnnotationConfigApplicationContext(App.class);

        Cafe cafe = ctx.getBean(Cafe.class);

        while (true) {
            Thread.sleep(1000);
            List<OrderItem> orders = new ArrayList<>();
            int countOrders = RandomUtils.nextInt(1, MENU.length);
            for (int i = 0; i < countOrders; i++) {
                OrderItem orderItem = generateOrderItem();
                orders.add(orderItem);
            }
            System.out.println("New orderItem: " + orders.toString());
            List<Food> foods = cafe.process(orders);
            System.out.println("Ready food: " + foods.toString());
        }
    }

    private static OrderItem generateOrderItem() {
        return new OrderItem(MENU[RandomUtils.nextInt(0, MENU.length)]);
    }

    @Bean
    public DirectChannel itemsChannel() {
        return MessageChannels.direct().datatype(List.class).get();
    }

    @Bean
    public PublishSubscribeChannel foodChannel() {
        return MessageChannels.publishSubscribe().datatype(List.class).get();
    }

    @Bean(name = PollerMetadata.DEFAULT_POLLER)
    public PollerMetadata defaultPoller() {
        PollerMetadata pollerMetadata = new PollerMetadata();
        pollerMetadata.setTrigger(new PeriodicTrigger(1000));
        return pollerMetadata;
    }

    @Bean
    public IntegrationFlow cafeFlow() {
        return IntegrationFlows.from("itemsChannel")
                .split()
                .transform(
                        (OrderItem s) ->
                                s.getItemName().replace("water", "VINE!")
                )
                .log()
                .handle("kitchenService", "cook")
                .log()
                .aggregate()
                .log()
                .channel("foodChannel")
                .log()
                .get();
    }




    public static void mainOldTestHW25(String[] args) throws InterruptedException {
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
