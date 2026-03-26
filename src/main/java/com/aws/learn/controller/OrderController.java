package com.aws.learn.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.aws.learn.dto.OrderDto;
import com.aws.learn.service.OrderService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/order")
public class OrderController {

    private final OrderService orderService;

    @PostMapping
    public ResponseEntity<String> saveProduct(
            @RequestBody OrderDto orderDto) {
        try {
            orderService.saveOrder(orderDto);
            return ResponseEntity.status(HttpStatus.OK).body("Order Placed.");
        } catch (Exception ex) {
            ex.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ex.getMessage());
        }
    }

    @PostMapping("/all")
    public ResponseEntity<String> saveAllOrder(@RequestBody List<OrderDto> orderDto) {
        try {
            orderService.saveAllProduct(orderDto);
            return ResponseEntity.status(HttpStatus.OK).body("All orders placed.");
        } catch (Exception ex) {
            ex.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ex.getMessage());
        }
    }

    @GetMapping("/all")
    public ResponseEntity<?> getAllOrders() {
        try {
            List<OrderDto> allProducts = orderService.readAllOrder();
            return ResponseEntity.status(HttpStatus.OK).body(allProducts);
        } catch (Exception ex) {
            ex.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ex.getMessage());
        }
    }

    @GetMapping
    public ResponseEntity<?> getOrderById(@RequestParam String id) {
        try {
            OrderDto allProducts = orderService.readOrderById(id);
            return ResponseEntity.status(HttpStatus.OK).body(allProducts);
        } catch (Exception ex) {
            ex.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ex.getMessage());
        }
    }

}
