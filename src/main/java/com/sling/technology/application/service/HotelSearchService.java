package com.sling.technology.application.service;

import com.sling.technology.domain.model.HotelSearch;
import com.sling.technology.domain.repository.SearchMessagePublisher;
import org.springframework.stereotype.Service;

@Service
public class HotelSearchService {
    private final SearchMessagePublisher searchMessagePublisher;

    public HotelSearchService(SearchMessagePublisher searchMessagePublisher) {
        this.searchMessagePublisher = searchMessagePublisher;
    }

    public String execute(HotelSearch hotelSearch){
        searchMessagePublisher.publish(hotelSearch);

        return hotelSearch.searchId();
    }
}
