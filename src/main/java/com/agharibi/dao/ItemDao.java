package com.agharibi.dao;

import com.agharibi.domain.Item;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.amazonaws.services.dynamodbv2.model.PutItemRequest;

import java.util.HashMap;
import java.util.Map;

public class ItemDao {

    private final AmazonDynamoDB dynamoDB;

    public ItemDao(AmazonDynamoDB dynamoDB) {
        this.dynamoDB = dynamoDB;
    }

    public void put(Item item) {
        Map<String, AttributeValue> itemMap = new HashMap<>();

        itemMap.put("id",
                new AttributeValue().withS(item.getId()));

        if (item.getName() != null) {
            itemMap.put("name",
                    new AttributeValue().withS(item.getName()));
        }

        if (item.getDescription() != null) {
            itemMap.put("description",
                    new AttributeValue().withS(item.getDescription()));
        }

        itemMap.put("totalRating",
                new AttributeValue().withN(Integer.toString(item.getTotalRating())));

        itemMap.put("totalComments",
                new AttributeValue().withN(Integer.toString(item.getTotalComments())));

        PutItemRequest putItemRequest = new PutItemRequest("Items", itemMap);
        dynamoDB.putItem(putItemRequest);
    }


}
