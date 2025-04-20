package com.example.tseytwa.CreateOrderMicroservice.saga;

import com.example.tseytwa.core.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.KafkaHandler;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Component
@KafkaListener(topics = {
        "${order.event.topic.name}",
        "${payment.events.topic.name}",
        "${notification.event.topic.name}"
})
public class OrderSaga {

    private final KafkaTemplate<String, Object> kafkaTemplate;
    private final String paymentTopicName;
    private final String notificationTopicName;
    private final String orderTopicName;
    private final Logger LOGGER = LoggerFactory.getLogger(OrderSaga.class);

    public OrderSaga(KafkaTemplate<String, Object> kafkaTemplate,
                     @Value("${payment.order.command.topic.name}") String topicName,
                     @Value("${notification.command.topic.name}") String notificationTopicName,
                     @Value("${order.commands.topic.name}") String orderTopicName) {
        this.kafkaTemplate = kafkaTemplate;
        this.paymentTopicName = topicName;
        this.notificationTopicName = notificationTopicName;
        this.orderTopicName = orderTopicName;
    }

    @KafkaHandler
    public void handleEvent(@Payload OrderCreatedEvent event) {
        PayOrderCommand command = new PayOrderCommand();
        command.setOrderId(event.getId());
        command.setDoctorSurname(event.getDoctorSurname());
        command.setPayWay(event.getPayWay());
        command.setPrice(event.getPrice());
        command.setCustomerId(event.getCustomerId());

        kafkaTemplate.send(paymentTopicName, command);
    }

    @KafkaHandler
    public void handleEvent(@Payload PaymentProcessedEvent event) {
        NotificationCommand command = new NotificationCommand();
        command.setCustomerId(event.getCustomerId());
        command.setPrice(event.getPrice());

        kafkaTemplate.send(notificationTopicName, command);

    }

    @KafkaHandler
    public void handleEvent(@Payload NotificationSuccessEvent event){
        LOGGER.info("Круг замкнулся "+event.getMessage());
    }

    @KafkaHandler
    public void handleEvent(@Payload PaymentFailedEvent event){
        CancelOrderCommand orderCommand = new CancelOrderCommand();
        orderCommand.setId(event.getCustomerId());
        System.out.println("failed event");
        kafkaTemplate.send(orderTopicName, orderCommand);
    }


}
