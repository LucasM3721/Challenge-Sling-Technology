package com.sling.technology.application.service;

import com.sling.technology.application.port.in.CountUseCase;
import com.sling.technology.domain.exception.SearchNotFoundException;
import com.sling.technology.domain.model.HotelSearch;
import com.sling.technology.domain.model.SearchCount;
import com.sling.technology.domain.repository.SearchRepository;

import static com.sling.technology.domain.exception.DomainMessages.SEARCH_NOT_FOUND;

/**
 * Use case implementation for counting identical hotel searches.
 */
public class SearchCountService implements CountUseCase {

    private final SearchRepository searchRepository;

    public SearchCountService(SearchRepository searchRepository) {
        this.searchRepository = searchRepository;
    }

    @Override
    public SearchCount execute(String searchId) {
        HotelSearch search = searchRepository.findById(searchId)
                .orElseThrow(() -> new SearchNotFoundException(SEARCH_NOT_FOUND));

        long count = searchRepository.countExactSearches(search);

        return new SearchCount(searchId, search, count);
    }
}
