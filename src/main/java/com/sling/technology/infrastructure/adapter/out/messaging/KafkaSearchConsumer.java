package com.sling.technology.infrastructure.adapter.out.messaging;

import com.sling.technology.domain.model.HotelSearch;
import com.sling.technology.domain.repository.SearchRepository;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import static com.sling.technology.utils.Constants.HOTEL_SEARCH_KAFKA_GROUP;
import static com.sling.technology.utils.Constants.HOTEL_SEARCH_KAFKA_TOPIC;

/**
 * Kafka consumer that persists hotel searches to the database.
 * Virtual threads are enabled via application.properties for better scalability.
 */
@Component
public class KafkaSearchConsumer {

    private final SearchRepository searchRepository;

    public KafkaSearchConsumer(SearchRepository searchRepository) {
        this.searchRepository = searchRepository;
    }

    @KafkaListener(topics = HOTEL_SEARCH_KAFKA_TOPIC, groupId = HOTEL_SEARCH_KAFKA_GROUP)
    public void consume(HotelSearch hotelSearch) {
        searchRepository.save(hotelSearch);
    }
}
