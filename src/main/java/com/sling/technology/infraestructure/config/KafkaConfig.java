package com.sling.technology.infraestructure.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

import static com.sling.technology.utils.Constants.HOTEL_SEARCH_KAFKA_TOPIC;

@Configuration
public class KafkaConfig {

    @Value(HOTEL_SEARCH_KAFKA_TOPIC)
    private String topicName;

    public NewTopic searchTopic() {
        return TopicBuilder.name(topicName)
                .partitions(1)
                .replicas(1)
                .build();
    }
}
