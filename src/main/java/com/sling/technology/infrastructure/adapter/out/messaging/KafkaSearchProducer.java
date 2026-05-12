package com.sling.technology.infrastructure.adapter.out.messaging;

import com.sling.technology.domain.model.HotelSearch;
import com.sling.technology.domain.repository.SearchMessagePublisher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

/**
 * Kafka producer that publishes hotel searches to the message broker.
 */
@Component
public class KafkaSearchProducer implements SearchMessagePublisher {

    private static final Logger log = LoggerFactory.getLogger(KafkaSearchProducer.class);

    private final KafkaTemplate<String, HotelSearch> kafkaTemplate;
    private final String topic;

    public KafkaSearchProducer(
            KafkaTemplate<String, HotelSearch> kafkaTemplate,
            @Value("${app.kafka.topic.search}") String topic) {

        this.kafkaTemplate = kafkaTemplate;
        this.topic = topic;
    }

    @Override
    public void publish(HotelSearch hotelSearch) {
        kafkaTemplate.send(topic, hotelSearch.searchId(), hotelSearch)
                .whenComplete((result, ex) -> {
                    if (ex != null) {
                        log.error("Failed to send message for searchId: {}", hotelSearch.searchId(), ex);
                    }
                });
    }
}
