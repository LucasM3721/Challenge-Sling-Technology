package com.sling.technology.infraestructure.adapter.out.persistance;

import com.sling.technology.domain.model.HotelSearch;
import com.sling.technology.domain.repository.SearchRepository;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class SearchRepositoryAdapter implements SearchRepository {
    @Override
    public void save(HotelSearch search) {

    }

    @Override
    public Optional<HotelSearch> findById(String searchId) {
        return Optional.empty();
    }

    @Override
    public long countExactSearches(HotelSearch search) {
        return 0;
    }
}
