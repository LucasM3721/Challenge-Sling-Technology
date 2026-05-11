package com.sling.technology.domain.repository;

import com.sling.technology.domain.model.HotelSearch;

/**
 * Port for publishing hotel searches to a message broker.
 */
public interface SearchMessagePublisher {
    void publish(HotelSearch hotelSearch);
}
