package com.agharibi.dao;

import com.agharibi.domain.Comment;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBScanExpression;

import java.util.List;

public class CommentDao {

    private DynamoDBMapper mapper;

    public CommentDao(AmazonDynamoDB dynamoDB) {
        this.mapper = new DynamoDBMapper(dynamoDB);
    }

    public Comment put(Comment comment) {
        mapper.save(comment);
        return comment;
    }

    public Comment get(String itemId, String messageId) {
        Comment comment = new Comment();
        comment.setItemId(itemId);
        comment.setMessageId(messageId);

        return mapper.load(comment);
    }

    public void delete(String itemId, String messageId) {
        Comment comment = new Comment();
        comment.setItemId(itemId);
        comment.setMessageId(messageId);

        mapper.delete(comment);
    }

    public List<Comment> getAll() {
        return mapper.scan(Comment.class, new DynamoDBScanExpression());
    }
}




