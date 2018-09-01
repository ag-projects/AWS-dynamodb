package com.agharibi;

import com.agharibi.dao.ItemDao;
import com.agharibi.domain.Item;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;

import java.util.UUID;

public class Driver {

    public static void main(String[] args) {

        AmazonDynamoDB client = AmazonDynamoDBClientBuilder.standard()
                .withRegion(Regions.US_EAST_1)
                .build();
        lowLevelDemo(client);
    }

    private static void lowLevelDemo(AmazonDynamoDB client) {
        ItemDao itemDao = new ItemDao(client);

        Item item = new Item();
        item.setId(UUID.randomUUID().toString());
        item.setName("Bitcoin miner");
        itemDao.put(item);

    }
}
