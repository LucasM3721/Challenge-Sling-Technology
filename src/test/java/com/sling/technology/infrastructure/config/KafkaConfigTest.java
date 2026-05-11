package com.sling.technology.infrastructure.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;

import static com.sling.technology.utils.Constants.HOTEL_SEARCH_KAFKA_TOPIC;
import static org.junit.jupiter.api.Assertions.*;

public class KafkaConfigTest {

    private KafkaConfig kafkaConfig;

    @BeforeEach
    void setUp() throws Exception {
        kafkaConfig = new KafkaConfig();
        
        Field topicNameField = KafkaConfig.class.getDeclaredField("topicName");
        topicNameField.setAccessible(true);
        topicNameField.set(kafkaConfig, HOTEL_SEARCH_KAFKA_TOPIC);
    }

    @Test
    void shouldCreateSearchTopicWithCorrectConfiguration() {
        NewTopic topic = kafkaConfig.searchTopic();

        assertNotNull(topic);
        assertEquals(HOTEL_SEARCH_KAFKA_TOPIC, topic.name());
        assertEquals(1, topic.numPartitions());
        assertEquals(1, topic.replicationFactor());
    }
}
