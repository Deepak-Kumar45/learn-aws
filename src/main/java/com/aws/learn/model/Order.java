package com.aws.learn.model;

import java.time.Instant;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbBean;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbPartitionKey;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@DynamoDbBean
public class Order {
    private String id;
    private String orderId;        // Partition key
    private String userId;
    private Integer quantity;
    private Double unitPrice;
    private Double totalAmount;
    private String status;         // PENDING, CONFIRMED, SHIPPED, DELIVERED, CANCELLED
    private String paymentMethod;  // CARD, UPI, COD, NET_BANKING
    private Instant orderDate;

    @DynamoDbPartitionKey
    public String getId(){
        return this.id;
    }
}