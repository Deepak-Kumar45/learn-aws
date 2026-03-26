package com.aws.learn.service;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.aws.learn.dto.OrderDto;
import com.aws.learn.model.Order;
import com.aws.learn.repository.OrderRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;

    public void saveOrder(OrderDto orderDto) {
        Order order = Order.builder()
                .id(UUID.randomUUID().toString())
                .orderId("ORD-" + System.currentTimeMillis()) // ⚠️ see note below
                .userId(orderDto.getUserId())
                .quantity(orderDto.getQuantity())
                .unitPrice(orderDto.getUnitPrice())
                .totalAmount(orderDto.getUnitPrice() * orderDto.getQuantity()) // ✅ calculate don't trust client
                .status("PENDING") // ✅ always force PENDING on creation, don't trust client
                .paymentMethod(orderDto.getPaymentMethod())
                .orderDate(Instant.now()) // ✅ always set server-side
                .build();
        orderRepository.save(order);
    }

    public OrderDto readOrderById(String id) {
        if (id == null || id.isBlank()) {
            throw new IllegalArgumentException("id cannot be null or empty");
        }

        Order order = orderRepository.getOrders(id);

        return OrderDto.builder()
                .id(order.getId())
                .orderId(order.getOrderId())
                .userId(order.getUserId())
                .quantity(order.getQuantity())
                .unitPrice(order.getUnitPrice())
                .totalAmount(order.getTotalAmount())
                .status(order.getStatus())
                .paymentMethod(order.getPaymentMethod())
                .orderDate(order.getOrderDate())
                .build();
    }

    public List<OrderDto> readAllOrder() {
        return orderRepository.getAllOrders().stream().map(order->{
            return OrderDto.builder()
                .id(order.getId())
                .orderId(order.getOrderId())
                .userId(order.getUserId())
                .quantity(order.getQuantity())
                .unitPrice(order.getUnitPrice())
                .totalAmount(order.getTotalAmount())
                .status(order.getStatus())
                .paymentMethod(order.getPaymentMethod())
                .orderDate(order.getOrderDate())
                .build();
        }).toList();
    }

    public void saveAllProduct(List<OrderDto> orderDto) {
        orderDto.stream().forEach(dto->this.saveOrder(dto));
    }
    

}
