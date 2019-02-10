package ru.sergey_gusarov.hw25.kitchen;

import org.springframework.stereotype.Service;
import ru.sergey_gusarov.hw25.domain.Food;
import ru.sergey_gusarov.hw25.domain.OrderItem;


@Service
public class KitchenService {

    public Food cook(OrderItem orderItem) throws Exception {
        System.out.println("Cooking " + orderItem.getItemName());
        Thread.sleep(100);
        System.out.println("Cooking " + orderItem.getItemName() + " done");
        return new Food(orderItem.getItemName());
    }
}
