package com.sling.technology.application.service;

import com.sling.technology.domain.model.HotelSearch;
import com.sling.technology.domain.repository.SearchMessagePublisher;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class HotelSearchServiceTest {

    @Mock
    private SearchMessagePublisher mockSearchMessagePublisher;

    @InjectMocks
    private HotelSearchService hotelSearchService;

    @Test
    void shouldPublishAndReturnStringId(){
        HotelSearch hotelSearch = new HotelSearch(
                "hotel1", LocalDate.now(), LocalDate.now().plusDays(1), List.of(24), "searchId");

        String searchId = hotelSearchService.execute(hotelSearch);

        assertNotNull(searchId);

        verify(mockSearchMessagePublisher, times(1)).publish(hotelSearch);
    }
}
