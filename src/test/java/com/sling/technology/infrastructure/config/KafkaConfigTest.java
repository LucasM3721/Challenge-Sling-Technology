package com.sling.technology.infrastructure.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;

import static org.junit.jupiter.api.Assertions.*;

class KafkaConfigTest {

    private static final String TEST_TOPIC_NAME = "hotel_availability_searches";

    private KafkaConfig kafkaConfig;

    @BeforeEach
    void setUp() throws Exception {
        kafkaConfig = new KafkaConfig();

        Field topicNameField = KafkaConfig.class.getDeclaredField("topicName");
        topicNameField.setAccessible(true);
        topicNameField.set(kafkaConfig, TEST_TOPIC_NAME);
    }

    @Test
    void shouldCreateSearchTopicWithCorrectConfiguration() {
        NewTopic topic = kafkaConfig.searchTopic();

        assertAll(
                () -> assertNotNull(topic),
                () -> assertEquals(TEST_TOPIC_NAME, topic.name()),
                () -> assertEquals(1, topic.numPartitions()),
                () -> assertEquals(1, topic.replicationFactor())
        );
    }
}
