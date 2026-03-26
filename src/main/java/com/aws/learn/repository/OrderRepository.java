package com.aws.learn.repository;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.aws.learn.model.Order;

import software.amazon.awssdk.enhanced.dynamodb.DynamoDbEnhancedClient;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbTable;
import software.amazon.awssdk.enhanced.dynamodb.Expression;
import software.amazon.awssdk.enhanced.dynamodb.Key;
import software.amazon.awssdk.enhanced.dynamodb.TableSchema;

@Repository
public class OrderRepository {

    private final DynamoDbTable<Order> dynamoDbTable;

    public OrderRepository(DynamoDbEnhancedClient dbEnhancedClient){
        this.dynamoDbTable = dbEnhancedClient.table("tbl_order", TableSchema.fromBean(Order.class));
    }

    // create/update an item
    public void save(Order order){
        dynamoDbTable.putItem(order);
    }

    // read
    public Order getOrders(String id){
        Key key = Key.builder().partitionValue(id).build();
        return this.dynamoDbTable.getItem(key);
    }

    // read all
    public List<Order> getAllOrders(){
        List<Order> list = this.dynamoDbTable.scan().items().stream().toList();
        return list;
    }

    // delete 
    public String deleteOrder(String id){
        Key key=Key.builder().partitionValue(id).build();
        Order deleteItem = this.dynamoDbTable.deleteItem(key);
        return deleteItem.getId();
    }

    // ── Conditional update ────────────────────────────
    public Order update(Order order) {
        return this.dynamoDbTable.updateItem(r -> r
                .item(order)
                .conditionExpression(Expression.builder()
                        .expression("attribute_exists(productId)")
                        .build()));
    }
}
