package com.sling.technology.infrastructure.adapter.out.messaging;

import com.sling.technology.domain.model.HotelSearch;
import com.sling.technology.domain.repository.SearchMessagePublisher;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import static com.sling.technology.utils.Constants.HOTEL_SEARCH_KAFKA_TOPIC;

/**
 * Kafka producer that publishes hotel searches to the message broker.
 */
@Component
public class KafkaSearchProducer implements SearchMessagePublisher {

    private final KafkaTemplate<String, HotelSearch> kafkaTemplate;
    private final String topic;

    public KafkaSearchProducer(
            KafkaTemplate<String, HotelSearch> kafkaTemplate,
            @Value(HOTEL_SEARCH_KAFKA_TOPIC) String topic) {

        this.kafkaTemplate = kafkaTemplate;
        this.topic = topic;
    }

    @Override
    public void publish(HotelSearch hotelSearch) {
        kafkaTemplate.send(topic, hotelSearch.searchId(), hotelSearch);
    }
}
