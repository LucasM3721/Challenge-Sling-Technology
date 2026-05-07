package com.sling.technology.infraestructure.adapter.out.messaging;

import com.sling.technology.domain.model.HotelSearch;
import com.sling.technology.domain.repository.SearchRepository;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import static com.sling.technology.utils.Constants.HOTEL_SEARCH_KAFKA_TOPIC;

@Component
public class KafkaSearchConsumer {

    private final SearchRepository searchRepository;

    public KafkaSearchConsumer(SearchRepository searchRepository) {
        this.searchRepository = searchRepository;
    }

    @KafkaListener(topics = HOTEL_SEARCH_KAFKA_TOPIC)
    public void consume(HotelSearch hotelSearch){
        searchRepository.save(hotelSearch);
    }
}
