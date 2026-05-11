package com.sling.technology.infrastructure.adapter.out.persistence;

import com.sling.technology.domain.model.HotelSearch;
import com.sling.technology.domain.repository.SearchRepository;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class SearchRepositoryAdapter implements SearchRepository {

    private final SpringDataSearchRepository jpaRepository;

    public SearchRepositoryAdapter(SpringDataSearchRepository jpaRepository) {
        this.jpaRepository = jpaRepository;
    }

    @Override
    public void save(HotelSearch search) {
        String agesStr = serializeAges(search.ages());
        SearchEntity searchEntity = new SearchEntity(
                search.searchId(),
                search.hotelId(),
                search.checkIn(),
                search.checkOut(),
                agesStr
        );

        jpaRepository.save(searchEntity);
    }

    @Override
    public Optional<HotelSearch> findById(String searchId) {
        return jpaRepository.findById(searchId)
                .map(entity -> new HotelSearch(
                        entity.getHotelId(),
                        entity.getCheckIn(),
                        entity.getCheckOut(),
                        deserializeAges(entity.getAges()),
                        entity.getSearchId()
                ));
    }

    @Override
    public long countExactSearches(HotelSearch search) {
        return jpaRepository.countByHotelIdAndCheckInAndCheckOutAndAges(
                search.hotelId(),
                search.checkIn(),
                search.checkOut(),
                serializeAges(search.ages())
        );
    }

    private String serializeAges(List<Integer> ages) {
        return ages.stream()
                .map(String::valueOf)
                .collect(Collectors.joining(","));
    }

    private List<Integer> deserializeAges(String agesStr) {
        if (agesStr == null || agesStr.isEmpty()) {
            return List.of();
        }
        return Arrays.stream(agesStr.split(","))
                .map(Integer::parseInt)
                .toList();
    }
}
