package com.sling.technology.application.service;

import com.sling.technology.domain.model.HotelSearch;
import com.sling.technology.domain.repository.SearchMessagePublisher;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class HotelSearchServiceTest {

    @Mock
    private SearchMessagePublisher mockSearchMessagePublisher;

    private HotelSearchService hotelSearchService;

    @BeforeEach
    void setUp() {
        hotelSearchService = new HotelSearchService(mockSearchMessagePublisher);
    }

    @Test
    void shouldPublishAndReturnStringId(){
        LocalDate tomorrow = LocalDate.now().plusDays(1);
        LocalDate dayAfterTomorrow = LocalDate.now().plusDays(2);

        HotelSearch hotelSearch = new HotelSearch(
                "hotel1", tomorrow, dayAfterTomorrow, List.of(24), "searchId");

        String searchId = hotelSearchService.execute(hotelSearch);

        assertNotNull(searchId);

        verify(mockSearchMessagePublisher, times(1)).publish(hotelSearch);
    }
}
