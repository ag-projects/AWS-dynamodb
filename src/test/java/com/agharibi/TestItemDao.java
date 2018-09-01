package com.agharibi;

import com.agharibi.dao.ItemDao;
import com.agharibi.domain.Item;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.local.embedded.DynamoDBEmbedded;
import org.junit.Before;
import org.junit.Test;

import static junit.framework.TestCase.assertNotNull;

public class TestItemDao {

    static AmazonDynamoDB dynamoDB;
    static ItemDao itemDao;

    @Before
    public void before() {
        dynamoDB = DynamoDBEmbedded.create().amazonDynamoDB();
        Utils.createTables(dynamoDB);
        itemDao = new ItemDao(dynamoDB);
    }

    @Test
    public void testDynamoDB() {
        Item item = itemDao.put(new Item());
        assertNotNull(itemDao.get(item.getId()));
    }
}
