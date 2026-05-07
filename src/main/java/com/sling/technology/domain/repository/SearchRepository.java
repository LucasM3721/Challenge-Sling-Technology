package com.sling.technology.domain.repository;

import com.sling.technology.domain.model.HotelSearch;

import java.util.Optional;

public interface SearchRepository {
    void save(HotelSearch search);
    Optional<HotelSearch> findById(String searchId);
    long countExactSearches(HotelSearch search);
}
