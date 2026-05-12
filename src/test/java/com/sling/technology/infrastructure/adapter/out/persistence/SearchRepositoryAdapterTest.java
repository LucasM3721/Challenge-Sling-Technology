package com.sling.technology.infrastructure.adapter.out.persistence;

import com.sling.technology.domain.model.HotelSearch;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class SearchRepositoryAdapterTest {

    @Mock
    private SpringDataSearchRepository jpaRepository;

    @InjectMocks
    private SearchRepositoryAdapter searchRepositoryAdapter;

    @Captor
    ArgumentCaptor<SearchEntity> entityCaptor;

    private final LocalDate checkIn = LocalDate.now().plusDays(1);
    private final LocalDate checkOut = LocalDate.now().plusDays(2);

    @BeforeEach
    void setUp() {
        searchRepositoryAdapter = new SearchRepositoryAdapter(jpaRepository);
    }

    @Test
    void shouldInitializeAdapterWithMockedDependency() {
        // Verify that the adapter is properly initialized with the mocked repository
        assertNotNull(searchRepositoryAdapter);
    }

    @Test
    void shouldSaveHotelSearchWithCorrectEntityMapping() {
        HotelSearch hotelSearch = createValidHotelSearch("test-search-id");

        searchRepositoryAdapter.save(hotelSearch);

        verify(jpaRepository).save(entityCaptor.capture());
        SearchEntity capturedEntity = entityCaptor.getValue();

        assertAll(
                () -> assertEquals("test-search-id", capturedEntity.getSearchId(),
                        "SearchEntity searchId should match HotelSearch searchId value"),
                () -> assertEquals("hotel123", capturedEntity.getHotelId(),
                        "SearchEntity hotelId should match HotelSearch hotelId"),
                () -> assertEquals(checkIn, capturedEntity.getCheckIn(),
                        "SearchEntity checkIn should match HotelSearch checkIn"),
                () -> assertEquals(checkOut, capturedEntity.getCheckOut(),
                        "SearchEntity checkOut should match HotelSearch checkOut"),
                () -> assertEquals("24,30,7", capturedEntity.getAges(),
                        "SearchEntity ages should be serialized as comma-separated string")
        );
    }

    @Test
    void shouldFindByIdWhenSearchExists() {
        String searchIdValue = "existing-search-id";
        SearchEntity entity = createSearchEntity(searchIdValue);
        when(jpaRepository.findById(searchIdValue)).thenReturn(Optional.of(entity));

        Optional<HotelSearch> result = searchRepositoryAdapter.findById(searchIdValue);

        assertTrue(result.isPresent(), "Result should be present when entity exists");
        HotelSearch hotelSearch = result.get();

        assertAll(
                () -> assertEquals(searchIdValue, hotelSearch.searchId(),
                        "HotelSearch searchId should match entity searchId"),
                () -> assertEquals("hotel123", hotelSearch.hotelId(),
                        "HotelSearch hotelId should match entity hotelId"),
                () -> assertEquals(checkIn, hotelSearch.checkIn(),
                        "HotelSearch checkIn should match entity checkIn"),
                () -> assertEquals(checkOut, hotelSearch.checkOut(),
                        "HotelSearch checkOut should match entity checkOut"),
                () -> assertEquals(List.of(24, 30, 7), hotelSearch.ages(),
                        "HotelSearch ages should be deserialized from comma-separated string")
        );

        verify(jpaRepository).findById(searchIdValue);
    }

    @Test
    void shouldReturnEmptyOptionalWhenSearchNotFound() {
        String nonExistingSearchId = "non-existing-search-id";
        when(jpaRepository.findById(nonExistingSearchId)).thenReturn(Optional.empty());

        Optional<HotelSearch> result = searchRepositoryAdapter.findById(nonExistingSearchId);

        assertTrue(result.isEmpty(), "Result should be empty when entity does not exist");
        verify(jpaRepository).findById(nonExistingSearchId);
    }

    @Test
    void shouldCountExactSearchesCorrectly() {
        HotelSearch hotelSearch = createValidHotelSearch("test-search-id");
        long expectedCount = 5L;
        when(jpaRepository.countByHotelIdAndCheckInAndCheckOutAndAges(
                "hotel123",
                checkIn,
                checkOut,
                "24,30,7"
        )).thenReturn(expectedCount);

        long actualCount = searchRepositoryAdapter.countExactSearches(hotelSearch);

        assertAll(
                () -> assertEquals(expectedCount, actualCount,
                        "Count should match the value returned by JPA repository"),
                () -> verify(jpaRepository).countByHotelIdAndCheckInAndCheckOutAndAges(
                        "hotel123",
                        checkIn,
                        checkOut,
                        "24,30,7")
        );
    }

    @Test
    void shouldSerializeAgesToCommaSeparatedString() {
        HotelSearch hotelSearch = createValidHotelSearch("serialize-test-id");

        searchRepositoryAdapter.save(hotelSearch);

        verify(jpaRepository).save(entityCaptor.capture());
        SearchEntity capturedEntity = entityCaptor.getValue();

        assertEquals("24,30,7", capturedEntity.getAges(),
                "Ages list [24,30,7] should be serialized to comma-separated string '24,30,7'");
    }

    @Test
    void shouldDeserializeAgesFromCommaSeparatedString() {
        String searchIdValue = "deserialize-test-id";
        SearchEntity entity = createSearchEntity(searchIdValue);
        when(jpaRepository.findById(searchIdValue)).thenReturn(Optional.of(entity));

        Optional<HotelSearch> result = searchRepositoryAdapter.findById(searchIdValue);

        assertTrue(result.isPresent(), "Result should be present");
        assertEquals(List.of(24, 30, 7), result.get().ages(),
                "Comma-separated string '24,30,7' should be deserialized to ages list [24,30,7]");
    }

    @Test
    void shouldReturnEmptyListWhenDeserializingEmptyString() throws Exception {
        String emptyAgesString = "";

        java.lang.reflect.Method deserializeMethod = SearchRepositoryAdapter.class
                .getDeclaredMethod("deserializeAges", String.class);
        deserializeMethod.setAccessible(true);

        @SuppressWarnings("unchecked")
        List<Integer> result = (List<Integer>) deserializeMethod.invoke(searchRepositoryAdapter, emptyAgesString);

        assertNotNull(result, "Result should not be null");
        assertTrue(result.isEmpty(),
                "Empty string should be deserialized to empty ages list");
    }

    @Test
    void shouldReturnEmptyListWhenDeserializingNull() throws Exception {
        String nullAgesString = null;

        java.lang.reflect.Method deserializeMethod = SearchRepositoryAdapter.class
                .getDeclaredMethod("deserializeAges", String.class);
        deserializeMethod.setAccessible(true);

        @SuppressWarnings("unchecked")
        List<Integer> result = (List<Integer>) deserializeMethod.invoke(searchRepositoryAdapter, nullAgesString);

        assertNotNull(result, "Result should not be null");
        assertTrue(result.isEmpty(),
                "Null ages string should be deserialized to empty ages list");
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

    private SearchEntity createSearchEntity(String searchId) {
        return new SearchEntity(
                searchId,
                "hotel123",
                checkIn,
                checkOut,
                "24,30,7"
        );
    }
}
