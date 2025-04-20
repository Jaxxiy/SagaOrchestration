package com.example.tseytwa.CreateOrderMicroservice.serivce.handler;

import com.example.tseytwa.CreateOrderMicroservice.dao.entity.Order;
import com.example.tseytwa.CreateOrderMicroservice.serivce.OrderService;
import com.example.tseytwa.core.CancelOrderCommand;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaHandler;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Component
@KafkaListener(topics = "${order.commands.topic.name}")
public class OrderHandler {

    private final Logger LOGGER = LoggerFactory.getLogger(OrderHandler.class);
    private final OrderService orderService;
    private final KafkaTemplate<String,Object> kafkaTemplate;

    public OrderHandler(OrderService orderService, KafkaTemplate<String, Object> kafkaTemplate) {
        this.orderService = orderService;
        this.kafkaTemplate = kafkaTemplate;
    }

    @KafkaHandler
    public void handler(@Payload CancelOrderCommand command) {
        System.out.println("tut");
        Order orderToCancel = new Order();
        orderToCancel.setId(command.getId());
        System.out.println(orderToCancel);
        orderService.cancelOrder(orderToCancel);
        LOGGER.info("Order cancelled");

    }
}
