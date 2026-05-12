package com.sling.technology.application.service;

import com.sling.technology.application.port.in.SearchUseCase;
import com.sling.technology.domain.model.HotelSearch;
import com.sling.technology.domain.repository.SearchMessagePublisher;

/**
 * Use case implementation for creating hotel searches.
 * Publishes the search to Kafka for async persistence.
 */
public class HotelSearchService implements SearchUseCase {
    private final SearchMessagePublisher searchMessagePublisher;

    public HotelSearchService(SearchMessagePublisher searchMessagePublisher) {
        this.searchMessagePublisher = searchMessagePublisher;
    }

    @Override
    public String execute(HotelSearch hotelSearch){
        searchMessagePublisher.publish(hotelSearch);

        return hotelSearch.searchId();
    }
}
