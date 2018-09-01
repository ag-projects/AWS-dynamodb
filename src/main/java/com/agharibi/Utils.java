package com.agharibi;

import com.agharibi.domain.Item;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.model.*;

public class Utils {

    public static void createTables(AmazonDynamoDB dynamoDB) {
        DynamoDBMapper dynamoDBMapper = new DynamoDBMapper(dynamoDB);
        createTable(Item.class, dynamoDBMapper, dynamoDB);
    }

    private static void createTable(Class<Item> itemClass, DynamoDBMapper dynamoDBMapper, AmazonDynamoDB dynamoDB) {
        CreateTableRequest createTableRequest = dynamoDBMapper
                .generateCreateTableRequest(itemClass);
        createTableRequest.withProvisionedThroughput(new ProvisionedThroughput(1L, 1L));

        if (!exists(dynamoDB, createTableRequest)) {
            dynamoDB.createTable(createTableRequest);
        }
        waitForTableCreated(createTableRequest.getTableName(), dynamoDB);
    }

    private static boolean exists(AmazonDynamoDB dynamoDB, CreateTableRequest createTableRequest) {
        try {
            dynamoDB.describeTable(createTableRequest.getTableName());
            return true;
        } catch (ResourceNotFoundException e) {
            return false;
        }
    }

    private static void waitForTableCreated(String tableName, AmazonDynamoDB dynamoDB) {
        while (true) {
            try {
                Thread.sleep(500);
                TableDescription table = dynamoDB.describeTable(new DescribeTableRequest(tableName)).getTable();
                if (table == null) {
                    continue;
                }

                String tableStatus = table.getTableStatus();
                if (tableStatus.equals(TableStatus.ACTIVE.toString())) {
                    return;
                }
            } catch (ResourceNotFoundException e) {
                // ignore

            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }
}
