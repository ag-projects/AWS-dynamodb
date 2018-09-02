package com.agharibi;

import com.agharibi.dao.CommentDao;
import com.agharibi.dao.ItemDao;
import com.agharibi.domain.Comment;
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
        // highLevelDemo(client);

        Utils.createTables(client);
        complexQueriesDemo(client);
    }

    private static void complexQueriesDemo(AmazonDynamoDB client) {
        CommentDao commentDao = new CommentDao(client);
        removeAll(commentDao);

        Comment c1 = newComment("1", "Delivered on time", "10", 5);
        Comment c2 = newComment("1", "Good Stuff", "10", 4);
        Comment c3 = newComment("1", "Not bad", "10", 1);
        Comment c4 = newComment("2", "It was OK", "10", 3);
        Comment c5 = newComment("3", "Delivered just in time", "10", 5);

        printComments(commentDao.getAll());

    }

    private static Comment newComment(String itemId, String msg, String userId, int rating) {
        Comment comment = new Comment();
        comment.setItemId(itemId);
        comment.setMessage(msg);
        comment.setUserId(userId);
        comment.setRating(rating);

        return comment;
    }

    private static void removeAll(CommentDao commentDao) {
        for (Comment comment : commentDao.getAll()) {
            commentDao.delete(comment.getItemId(), comment.getMessageId());
        }
    }

    private static void printComments(List<Comment> comments) {
        System.out.println(comments.stream()
        .map(Comment::toString)
        .collect(Collectors.joining("\n")));
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
