package com.aws.learn.dto;

import java.time.Instant;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
@JsonInclude(value = Include.NON_NULL)
public class OrderDto {
    private String id;
    private String orderId;        // Partition key
    private String userId;
    private Integer quantity;
    private Double unitPrice;
    private Double totalAmount;
    private String status;         // PENDING, CONFIRMED, SHIPPED, DELIVERED, CANCELLED
    private String paymentMethod;  // CARD, UPI, COD, NET_BANKING
    private Instant orderDate;
}
