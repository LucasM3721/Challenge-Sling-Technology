package com.sling.technology.domain.repository;

import com.sling.technology.domain.model.HotelSearch;

import java.util.Optional;

/**
 * Port for hotel search persistence operations.
 */
public interface SearchRepository {
    void save(HotelSearch search);
    Optional<HotelSearch> findById(String searchId);
    long countExactSearches(HotelSearch search);
}
