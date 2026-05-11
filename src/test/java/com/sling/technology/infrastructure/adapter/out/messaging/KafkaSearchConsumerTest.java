package com.sling.technology.infrastructure.adapter.out.messaging;

import com.sling.technology.domain.model.HotelSearch;
import com.sling.technology.domain.repository.SearchRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class KafkaSearchConsumerTest {

    @Mock
    private SearchRepository searchRepository;

    @InjectMocks
    private KafkaSearchConsumer kafkaSearchConsumer;

    @Captor
    private ArgumentCaptor<HotelSearch> hotelSearchCaptor;

    private final LocalDate checkIn = LocalDate.now();
    private final LocalDate checkOut = LocalDate.now().plusDays(1);

    @Test
    void shouldSaveSearchWhenMessageConsumed() {
        HotelSearch search = createValidHotelSearch("test-search-id");

        kafkaSearchConsumer.consume(search);

        verify(searchRepository, times(1)).save(search);
    }

    @Test
    void shouldPassCorrectSearchObjectToRepository() {
        HotelSearch search = createValidHotelSearch("unique-search-id");

        kafkaSearchConsumer.consume(search);

        verify(searchRepository).save(hotelSearchCaptor.capture());
        HotelSearch capturedSearch = hotelSearchCaptor.getValue();

        assertAll(
                () -> assertSame(search, capturedSearch, "The exact same HotelSearch object should be passed to repository"),
                () -> assertEquals("unique-search-id", capturedSearch.searchId()),
                () -> assertEquals("hotel123", capturedSearch.hotelId()),
                () -> assertEquals(checkIn, capturedSearch.checkIn()),
                () -> assertEquals(checkOut, capturedSearch.checkOut()),
                () -> assertEquals(List.of(24, 30, 7), capturedSearch.ages())
        );
    }

    private HotelSearch createValidHotelSearch(String searchId) {
        return new HotelSearch(
                "hotel123",
                checkIn,
                checkOut,
                List.of(24, 30, 7),
                searchId
        );
    }
}
