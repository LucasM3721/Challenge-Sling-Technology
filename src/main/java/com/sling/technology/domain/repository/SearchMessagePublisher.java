package com.sling.technology.domain.repository;

import com.sling.technology.domain.model.HotelSearch;

public interface SearchMessagePublisher {
    void publish(HotelSearch hotelSearch);
}
