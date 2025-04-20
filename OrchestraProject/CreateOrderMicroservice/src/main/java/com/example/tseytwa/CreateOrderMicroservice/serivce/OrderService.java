package com.example.tseytwa.CreateOrderMicroservice.serivce;

import com.example.tseytwa.CreateOrderMicroservice.dao.entity.Order;
import com.example.tseytwa.CreateOrderMicroservice.dao.repository.OrderRepository;
import com.example.tseytwa.CreateOrderMicroservice.dto.OrderDTO;
import com.example.tseytwa.core.OrderCreatedEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class OrderService {

    private final KafkaTemplate<String, Object> kafkaTemplate;
    private final OrderRepository orderRepository;
    private final String orderEventTopicName;
    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());


    @Autowired
    public OrderService(KafkaTemplate<String, Object> kafkaTemplate,
                        OrderRepository orderRepository,
                        @Value("${order.event.topic.name}") String orderEventTopicName) {
        this.kafkaTemplate = kafkaTemplate;
        this.orderRepository = orderRepository;
        this.orderEventTopicName = orderEventTopicName;
    }

    public List<Order> findAll() {
        return orderRepository.findAll();
    }

    public Order addOrder(OrderDTO order) {
        Order newOrder = new Order();
        newOrder.setId(UUID.randomUUID().toString());
        newOrder.setDoctorSurname(order.getDoctorSurname());
        newOrder.setPayWay(order.getPayWay());
        newOrder.setPrice(order.getPrice());
        newOrder.setCustomerId(order.getCustomerId());

        OrderCreatedEvent orderCreatedEvent = new OrderCreatedEvent();
        orderCreatedEvent.setId(newOrder.getId());
        orderCreatedEvent.setDoctorSurname(order.getDoctorSurname());
        orderCreatedEvent.setPayWay(order.getPayWay());
        orderCreatedEvent.setPrice(order.getPrice());
        orderCreatedEvent.setCustomerId(order.getCustomerId());

        kafkaTemplate.send(orderEventTopicName, orderCreatedEvent);

        LOGGER.info("Order: " + order + "sended to: "+ orderEventTopicName + "successfully!");

        return orderRepository.save(newOrder);
    }

    public void cancelOrder(Order orderToCancel) {
        List<Order> order = orderRepository.findAllByCustomerId(orderToCancel.getId());
        System.out.println(order);
        orderRepository.deleteAll(order);
        LOGGER.info("Order: " + order + "deleted successfully!");
    }
}
