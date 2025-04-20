package com.example.tseytwa.PaymentMicroservice.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;

@Configuration
public class KafkaConfig {

    @Value("${payment.events.topic.name}")
    private String eventsTopic;
    private static final Integer REPLICATION_FACTOR = 3;
    private static final Integer PARTITION = 3;

    @Bean
    public KafkaTemplate<String, Object> kafkaTemplate(ProducerFactory<String, Object> producerFactory) {
        return new KafkaTemplate<>(producerFactory);
    }

    @Bean
    public NewTopic paymentTopic() {
        return TopicBuilder.name(eventsTopic)
                .partitions(PARTITION)
                .replicas(REPLICATION_FACTOR)
                .build();
    }
}
