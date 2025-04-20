package com.example.tseytwa.CreateOrderMicroservice.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.apache.kafka.common.internals.Topic;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;

@Configuration
public class KafkaKonfig {

    @Value("${order.event.topic.name}")
    private String orderTopic;

    @Value("${payment.order.command.topic.name}")
    private String paymentOrderTopic;

    @Value("${notification.command.topic.name}")
    private String notificationTopic;

    @Value("${order.commands.topic.name}")
    private String orderCommandTopic;

    private static final Integer TOPIC_REPLICATION_FACTOR = 3;
    private static final Integer TOPIC_PARTITIONS = 3;

    @Bean
    public KafkaTemplate<String, Object> kafkaTemplate(ProducerFactory<String, Object> producerFactory) {
        return new KafkaTemplate<>(producerFactory);
    }

    @Bean
    public NewTopic createCommandOrderEventTopic() {
        return TopicBuilder.name(orderCommandTopic)
                .partitions(TOPIC_PARTITIONS)
                .replicas(TOPIC_REPLICATION_FACTOR)
                .build();
    }

    @Bean
    public NewTopic createOrderEventTopic() {
        return TopicBuilder.name(orderTopic)
                .partitions(TOPIC_PARTITIONS)
                .replicas(TOPIC_REPLICATION_FACTOR)
                .build();
    }

    @Bean NewTopic paymentOrderCommandTopic(){
        return TopicBuilder.name(paymentOrderTopic)
                .partitions(TOPIC_PARTITIONS)
                .replicas(TOPIC_REPLICATION_FACTOR)
                .build();
    }

    @Bean NewTopic notificationCommandTopic(){
        return TopicBuilder.name(notificationTopic)
                .partitions(TOPIC_PARTITIONS)
                .replicas(TOPIC_REPLICATION_FACTOR)
                .build();
    }


}
