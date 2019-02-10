package ru.sergey_gusarov.hw25.domain;

public class OrderItem {

    private final String itemName;

    public OrderItem(String itemName) {
        this.itemName = itemName;
    }

    public String getItemName() {
        return itemName;
    }

    @Override
    public String toString() {
        return "OrderItem{" +
                "itemName='" + itemName + '\'' +
                '}';
    }
}
