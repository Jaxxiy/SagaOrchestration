package com.example.tseytwa.NotificationMicroservice.service.handler;

import com.example.tseytwa.core.NotificationCommand;
import com.example.tseytwa.core.NotificationSuccessEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.KafkaHandler;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Component
@KafkaListener(topics = "${notification.command.topic.name}")
public class NotificationHandler {

    private final KafkaTemplate<String,Object> kafkaTemplate;
    private final String topicName;
    private final Logger logger = LoggerFactory.getLogger(NotificationHandler.class);

    public NotificationHandler(@Value("${notification.event.topic.name}") String topicName,
                               KafkaTemplate<String, Object> kafkaTemplate) {
        this.topicName = topicName;
        this.kafkaTemplate = kafkaTemplate;
    }

    @KafkaHandler
    public void handler(@Payload NotificationCommand command) {
        NotificationSuccessEvent event = new NotificationSuccessEvent();
        event.setMessage("Notification success "+command.getCustomerId());

        logger.info(event.toString());

        kafkaTemplate.send(topicName, event);
    }
}
