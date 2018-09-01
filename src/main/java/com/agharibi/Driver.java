package com.agharibi;

import com.agharibi.dao.ItemDao;
import com.agharibi.domain.Item;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;

import java.io.IOException;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

public class Driver {

    public static void main(String[] args) {

        AmazonDynamoDB client = AmazonDynamoDBClientBuilder.standard()
                .withRegion(Regions.US_EAST_1)
                .build();
        // lowLevelDemo(client);
        highLevelDemo(client);
    }

    private static void print(List<Item> all) {
        System.out.println(all.stream()
                .map(Item::toString)
                .collect(Collectors.joining("\n")));
    }

    private static Item newItem(String name, String description) {
        Item item = new Item();

        item.setName(name);
        item.setDescription(description);

        return item;
    }

    private static void pause() {
        System.out.println("PAUSE");
        try {
            System.in.read();
        }catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void highLevelDemo(AmazonDynamoDB client) {
        ItemDao itemDao = new ItemDao(client);

        Item item1 = itemDao.put(newItem("Mac Book Pro", "The very Best"));
        Item item2 = itemDao.put(newItem("Apple TV", "Black & White"));
        Item item3 = itemDao.put(newItem("Mac Desktop", "As good as always"));

        print(itemDao.getAll());
        pause();

        itemDao.delete(item2.getId());
        print(itemDao.getAll());

    }

    private static void lowLevelDemo(AmazonDynamoDB client) {
        ItemDao itemDao = new ItemDao(client);

        Item item = new Item();
        item.setId(UUID.randomUUID().toString());
        item.setName("Bitcoin miner");
        itemDao.put(item);

    }
}
