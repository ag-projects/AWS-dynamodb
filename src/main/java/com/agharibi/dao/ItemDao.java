package com.agharibi.dao;

import com.agharibi.domain.Item;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBScanExpression;

import java.util.List;

public class ItemDao {

    private final DynamoDBMapper mapper;

    public ItemDao(AmazonDynamoDB dynamoDB) {
        this.mapper = new DynamoDBMapper(dynamoDB);
    }

    public Item put(Item item) {
        mapper.save(item);
        return item;
    }

    public Item get(String id) {
        return mapper.load(Item.class, id);
    }

    public void delete(String id) {
        Item item = new Item();
        item.setId(id);
        mapper.delete(item);
    }

    public List<Item> getAll() {
        return mapper.scan(Item.class, new DynamoDBScanExpression());
    }
}
