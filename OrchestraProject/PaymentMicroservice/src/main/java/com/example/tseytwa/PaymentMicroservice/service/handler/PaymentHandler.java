package com.example.tseytwa.PaymentMicroservice.service.handler;

import com.example.tseytwa.PaymentMicroservice.dao.entity.Payment;
import com.example.tseytwa.PaymentMicroservice.service.PaymentService;
import com.example.tseytwa.core.PayOrderCommand;
import com.example.tseytwa.core.PaymentFailedEvent;
import com.example.tseytwa.core.PaymentProcessedEvent;
import com.example.tseytwa.core.exception.NotEnoughMoneyException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.KafkaHandler;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Component
@KafkaListener(topics = "${payment.order.command.topic.name}")
public class PaymentHandler {

    private final KafkaTemplate<String, Object> kafkaTemplate;
    private final PaymentService paymentService;
    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());
    private final String paymentEventTopicName;

    public PaymentHandler(KafkaTemplate<String, Object> kafkaTemplate,
                          PaymentService paymentService,
                          @Value("${payment.events.topic.name}") String paymentEventTopicName) {
        this.kafkaTemplate = kafkaTemplate;
        this.paymentService = paymentService;
        this.paymentEventTopicName = paymentEventTopicName;
    }

    @KafkaHandler
    public void paymentCommand(@Payload PayOrderCommand payOrderCommand) {
        System.out.println(payOrderCommand);
        try {
            paymentService.paymentCommand(payOrderCommand.getCustomerId(), payOrderCommand.getPrice());

            LOGGER.info("payment "+payOrderCommand.getCustomerId()+" successfully executed");

            PaymentProcessedEvent paymentProcessedEvent = new PaymentProcessedEvent();
            paymentProcessedEvent.setCustomerId(payOrderCommand.getCustomerId());
            paymentProcessedEvent.setPrice(payOrderCommand.getPrice());

            kafkaTemplate.send(paymentEventTopicName, paymentProcessedEvent);
        }catch (NotEnoughMoneyException e) {
            LOGGER.error(e.getMessage());

            PaymentFailedEvent paymentFailedEvent = new PaymentFailedEvent();
            paymentFailedEvent.setCustomerId(payOrderCommand.getCustomerId());
            paymentFailedEvent.setPrice(payOrderCommand.getPrice());

            kafkaTemplate.send(paymentEventTopicName, paymentFailedEvent);
        }
    }
}
