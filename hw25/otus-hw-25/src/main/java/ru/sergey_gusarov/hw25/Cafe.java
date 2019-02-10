package ru.sergey_gusarov.hw25;


import org.springframework.integration.annotation.Gateway;
import org.springframework.integration.annotation.MessagingGateway;
import ru.sergey_gusarov.hw25.domain.Food;
import ru.sergey_gusarov.hw25.domain.OrderItem;

import java.util.List;

@MessagingGateway
public interface Cafe {
    @Gateway(requestChannel = "itemsChannel", replyChannel = "foodChannel")
    List<Food> process(List<OrderItem> orderItem);


}
