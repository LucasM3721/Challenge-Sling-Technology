package com.sling.technology.domain.model;

import com.sling.technology.domain.exception.DomainException;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import static com.sling.technology.domain.exception.DomainMessages.*;
import static org.junit.jupiter.api.Assertions.*;

class HotelSearchTest {

    private final LocalDate tomorrow = LocalDate.now().plusDays(1);
    private final LocalDate dayAfterTomorrow = LocalDate.now().plusDays(2);

    @Test
    void shouldCreateSuccessfulHotelSearchWhenSearchIdIsNull() {
        HotelSearch hotelSearch = new HotelSearch(
                "hotel1", tomorrow, dayAfterTomorrow, List.of(24), null);

        assertAll(
                () -> assertEquals("hotel1", hotelSearch.hotelId()),
                () -> assertEquals(1, hotelSearch.ages().size()),
                () -> assertNotNull(hotelSearch.searchId())
        );
    }

    @Test
    void shouldCreateSuccessfulHotelSearchWhenSearchIdIsEmpty() {
        HotelSearch hotelSearch = new HotelSearch(
                "hotel1", tomorrow, dayAfterTomorrow, List.of(24), " ");

        assertAll(
                () -> assertEquals("hotel1", hotelSearch.hotelId()),
                () -> assertEquals(1, hotelSearch.ages().size()),
                () -> assertNotNull(hotelSearch.searchId())
        );
    }

    @Test
    void shouldThrowExceptionWhenHotelIdIsNull() {
        DomainException exception = assertThrows(DomainException.class, () ->
                new HotelSearch(null, tomorrow, dayAfterTomorrow, List.of(24), null));

        assertEquals(HOTEL_ID_REQUIRED, exception.getMessage());
    }

    @Test
    void shouldThrowExceptionWhenHotelIdIsEmpty() {
        DomainException exception = assertThrows(DomainException.class, () ->
                new HotelSearch("", tomorrow, dayAfterTomorrow, List.of(24), null));

        assertEquals(HOTEL_ID_REQUIRED, exception.getMessage());
    }

    @Test
    void shouldThrowExceptionWhenCheckInIsNull() {
        DomainException exception = assertThrows(DomainException.class, () ->
                new HotelSearch("hotel1", null, dayAfterTomorrow, List.of(24), null));

        assertEquals(CHECK_IN_REQUIRED, exception.getMessage());
    }

    @Test
    void shouldThrowExceptionWhenCheckOutIsNull() {
        DomainException exception = assertThrows(DomainException.class, () ->
                new HotelSearch("hotel1", tomorrow, null, List.of(24), null));

        assertEquals(CHECK_OUT_REQUIRED, exception.getMessage());
    }

    @Test
    void shouldThrowExceptionWhenCheckInIsInThePast() {
        LocalDate yesterday = LocalDate.now().minusDays(1);

        DomainException exception = assertThrows(DomainException.class, () ->
                new HotelSearch("hotel1", yesterday, tomorrow, List.of(24), null));

        assertEquals(CHECK_IN_PAST, exception.getMessage());
    }

    @Test
    void shouldThrowExceptionWhenCheckInIsAfterCheckOut() {
        DomainException exception = assertThrows(DomainException.class, () ->
                new HotelSearch("hotel1", dayAfterTomorrow, tomorrow, List.of(24), null));

        assertEquals(CHECK_IN_BEFORE_CHECK_OUT, exception.getMessage());
    }

    @Test
    void shouldThrowExceptionWhenCheckInEqualsCheckOut() {
        DomainException exception = assertThrows(DomainException.class, () ->
                new HotelSearch("hotel1", tomorrow, tomorrow, List.of(24), null));

        assertEquals(CHECK_IN_BEFORE_CHECK_OUT, exception.getMessage());
    }

    @Test
    void shouldThrowExceptionWhenAgesIsNull() {
        DomainException exception = assertThrows(DomainException.class, () ->
                new HotelSearch("hotel1", tomorrow, dayAfterTomorrow, null, null));

        assertEquals(AGES_REQUIRED, exception.getMessage());
    }

    @Test
    void shouldThrowExceptionWhenAgesIsEmpty() {
        DomainException exception = assertThrows(DomainException.class, () ->
                new HotelSearch("hotel1", tomorrow, dayAfterTomorrow, List.of(), null));

        assertEquals(AGES_REQUIRED, exception.getMessage());
    }

    @Test
    void shouldThrowExceptionWhenAgesHasNegativeNumbers() {
        DomainException exception = assertThrows(DomainException.class, () ->
                new HotelSearch("hotel1", tomorrow, dayAfterTomorrow, List.of(24, -1), null));

        assertEquals(AGE_INVALID, exception.getMessage());
    }

    @Test
    void shouldThrowExceptionWhenAgesHasNullNumbers() {
        DomainException exception = assertThrows(DomainException.class, () ->
                new HotelSearch("hotel1", tomorrow, dayAfterTomorrow, Arrays.asList(24, null), null));

        assertEquals(AGE_INVALID, exception.getMessage());
    }

    @Test
    void shouldPreserveAgesOrder() {
        List<Integer> ages = List.of(24, 30, 18);
        HotelSearch hotelSearch = new HotelSearch(
                "hotel1", tomorrow, dayAfterTomorrow, ages, null);

        assertEquals(ages, hotelSearch.ages());
    }

    @Test
    void shouldEnsureAgesIsImmutable(){
        HotelSearch hotelSearch = new HotelSearch(
                "hotel1", tomorrow, dayAfterTomorrow, List.of(24, 30, 18), null);

        List<Integer> ages = hotelSearch.ages();
        assertThrows(UnsupportedOperationException.class, () -> ages.add(40));
    }
}
