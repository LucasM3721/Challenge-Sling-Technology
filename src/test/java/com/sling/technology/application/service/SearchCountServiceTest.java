package com.sling.technology.application.service;

import com.sling.technology.domain.exception.DomainException;
import com.sling.technology.domain.model.HotelSearch;
import com.sling.technology.domain.model.SearchCount;
import com.sling.technology.domain.repository.SearchRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class SearchCountServiceTest {

    @Mock
    private SearchRepository searchRepository;

    @InjectMocks
    private SearchCountService searchCountService;

    @Test
    void shouldCountAndReturnSearchCount(){
        HotelSearch hotelSearch = new HotelSearch(
                "hotel1", LocalDate.now(), LocalDate.now().plusDays(1), List.of(24), "searchId");

        when(searchRepository.findById("searchId")).thenReturn(Optional.of(hotelSearch));
        when(searchRepository.countExactSearches(hotelSearch)).thenReturn(5L);

        SearchCount searchCount = searchCountService.execute("searchId");

        assertAll(
                () -> assertEquals("searchId", searchCount.searchId()),
                () -> assertEquals(5L, searchCount.count())
        );
    }

    @Test
    void shouldThrowExceptionWhenSearchIdNotFound(){
        when(searchRepository.findById("nonExistingSearchId")).thenReturn(Optional.empty());

        assertThrows(DomainException.class, () -> searchCountService.execute("nonExistingSearchId"));

    }
}
