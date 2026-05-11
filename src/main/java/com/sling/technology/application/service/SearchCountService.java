package com.sling.technology.application.service;

import com.sling.technology.application.port.in.CountUseCase;
import com.sling.technology.domain.exception.DomainException;
import com.sling.technology.domain.model.HotelSearch;
import com.sling.technology.domain.model.SearchCount;
import com.sling.technology.domain.repository.SearchRepository;
import org.springframework.stereotype.Service;

import static com.sling.technology.utils.Constants.DOMAIN_EXCEPTION_COUNT_SERVICE_SEARCH_ID;

@Service
public class SearchCountService implements CountUseCase {

    private final SearchRepository searchRepository;

    public SearchCountService(SearchRepository searchRepository) {
        this.searchRepository = searchRepository;
    }

    @Override
    public SearchCount execute(String searchId) {
        HotelSearch search = searchRepository.findById(searchId)
                .orElseThrow(() -> new DomainException(DOMAIN_EXCEPTION_COUNT_SERVICE_SEARCH_ID));

        long count = searchRepository.countExactSearches(search);

        return new SearchCount(searchId, search, count);
    }
}
