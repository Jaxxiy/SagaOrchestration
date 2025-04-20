package com.example.tseytwa.NotificationMicroservice.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;

@Configuration
public class KafkaConfig {

    @Value("${notification.event.topic.name}")
    private String topicName;
    private static final Integer TOPIC_PARTITIONS = 3;
    private static final Integer TOPIC_REPLICATION_FACTOR = 3;

    @Bean
    public KafkaTemplate<String, Object> kafkaTemplate(ProducerFactory<String, Object> producerFactory) {
        return new KafkaTemplate<>(producerFactory);
    }

    @Bean
    public NewTopic notificationTopic() {
        return TopicBuilder.name(topicName)
                .replicas(TOPIC_REPLICATION_FACTOR)
                .partitions(TOPIC_PARTITIONS)
                .build();
    }

}
