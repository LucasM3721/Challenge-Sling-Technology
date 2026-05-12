package com.sling.technology.infrastructure.adapter.out.messaging;

import com.sling.technology.domain.model.HotelSearch;
import com.sling.technology.domain.repository.SearchRepository;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

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

    @KafkaListener(topics = "${app.kafka.topic.search}", groupId = "${spring.kafka.consumer.group-id}")
    public void consume(HotelSearch hotelSearch) {
        searchRepository.save(hotelSearch);
    }
}
