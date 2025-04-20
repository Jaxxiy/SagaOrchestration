package com.example.tseytwa.CreateOrderMicroservice.controller;

import com.example.tseytwa.CreateOrderMicroservice.dao.entity.Order;
import com.example.tseytwa.CreateOrderMicroservice.dto.OrderDTO;
import com.example.tseytwa.CreateOrderMicroservice.serivce.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/order")
public class OrderController {
    private final OrderService orderService;

    @Autowired
    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @GetMapping
    public List<Order> findAll(){
        return orderService.findAll();
    }

    @PostMapping
    public Order addOrder(@RequestBody OrderDTO order){
        return orderService.addOrder(order);
    }

}
