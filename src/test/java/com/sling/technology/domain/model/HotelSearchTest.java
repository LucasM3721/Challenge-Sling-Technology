package com.sling.technology.domain.model;

import com.sling.technology.domain.exception.DomainException;
import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import static com.sling.technology.utils.Constants.*;
import static org.junit.jupiter.api.Assertions.*;

public class HotelSearchTest {

    @Test
    void shouldCreateSuccessfulHotelSearchTestWhenSearchIdIsNull() {
        HotelSearch hotelSearch = new HotelSearch(
                "hotel1", LocalDate.now(), LocalDate.now().plusDays(1), List.of(24), null);

        assertAll(
                () -> assertEquals("hotel1", hotelSearch.hotelId()),
                () -> assertEquals(1, hotelSearch.ages().size()),
                () -> assertNotNull(hotelSearch.searchId())
        );
    }

    @Test
    void shouldCreateSuccessfulHotelSearchTestWhenSearchIdIsEmpty() {
        HotelSearch hotelSearch = new HotelSearch(
                "hotel1", LocalDate.now(), LocalDate.now().plusDays(1), List.of(24), " ");

        assertAll(
                () -> assertEquals("hotel1", hotelSearch.hotelId()),
                () -> assertEquals(1, hotelSearch.ages().size()),
                () -> assertNotNull(hotelSearch.searchId())
        );
    }

    @Test
    void shouldThrowExceptionWhenHotelIdIsNull() {
        try {
            new HotelSearch(
                    null, LocalDate.now(), LocalDate.now().plusDays(1), List.of(24), null);
        } catch (DomainException e) {
            assertEquals(DOMAIN_EXCEPTION_HOTEL_SEARCH_HOTEL_ID, e.getMessage());
        }
    }

    @Test
    void shouldThrowExceptionWhenHotelIdIsEmpty() {
        try {
            new HotelSearch(
                    StringUtils.EMPTY, LocalDate.now(), LocalDate.now().plusDays(1), List.of(24), null);
        } catch (DomainException e) {
            assertEquals(DOMAIN_EXCEPTION_HOTEL_SEARCH_HOTEL_ID, e.getMessage());
        }
    }

    @Test
    void shouldThrowExceptionWhenCheckInIsNull(){
        try {
            new HotelSearch(
                    "hotel1", null, LocalDate.now().plusDays(1), List.of(24), null);
        } catch (DomainException e) {
            assertEquals(DOMAIN_EXCEPTION_HOTEL_SEARCH_CHECK_IN, e.getMessage());
        }
    }

    @Test
    void shouldThrowExceptionWhenCheckOutIsNull(){
        try {
            new HotelSearch(
                    "hotel1", LocalDate.now(), null, List.of(24), null);
        } catch (DomainException e) {
            assertEquals(DOMAIN_EXCEPTION_HOTEL_SEARCH_CHECK_OUT, e.getMessage());
        }
    }

    @Test
    void shouldThrowExceptionWhenCheckInIsAfterCheckOut(){
        try {
            new HotelSearch(
                    "hotel1", LocalDate.now(), LocalDate.now().plusDays(-1), List.of(24), null);
        } catch (DomainException e) {
            assertEquals(DOMAIN_EXCEPTION_HOTEL_SEARCH_CHECK_IN_BEFORE_CHECK_OUT, e.getMessage());
        }
    }

    @Test
    void shouldThrowExceptionWhenCheckInIsEqualsCheckOut(){
        LocalDate date = LocalDate.now();
        try {
            new HotelSearch(
                    "hotel1", date, date, List.of(24), null);
        } catch (DomainException e) {
            assertEquals(DOMAIN_EXCEPTION_HOTEL_SEARCH_CHECK_IN_BEFORE_CHECK_OUT, e.getMessage());
        }
    }

    @Test
    void shouldThrowExceptionWhenAgesIsNull(){
        try {
            new HotelSearch(
                    "hotel1", LocalDate.now(), LocalDate.now().plusDays(1), null, null);
        } catch (DomainException e) {
            assertEquals(DOMAIN_EXCEPTION_HOTEL_SEARCH_AGES, e.getMessage());
        }
    }

    @Test
    void shouldThrowExceptionWhenAgesIsEmpty(){
        try {
            new HotelSearch(
                    "hotel1", LocalDate.now(), LocalDate.now().plusDays(1), List.of(), null);
        } catch (DomainException e) {
            assertEquals(DOMAIN_EXCEPTION_HOTEL_SEARCH_AGES, e.getMessage());
        }
    }

    @Test
    void shouldThrowExceptionWhenAgesHasNegativeNumbers(){
        try {
            new HotelSearch(
                    "hotel1", LocalDate.now(), LocalDate.now().plusDays(1), List.of(24, -1), null);
        } catch (DomainException e) {
            assertEquals(DOMAIN_EXCEPTION_HOTEL_SEARCH_AGE_VALUE, e.getMessage());
        }
    }

    @Test
    void shouldThrowExceptionWhenAgesHasNullNumbers(){
        try {
            new HotelSearch(
                    "hotel1", LocalDate.now(), LocalDate.now().plusDays(1), Arrays.asList(24, null), null);
        } catch (DomainException e) {
            assertEquals(DOMAIN_EXCEPTION_HOTEL_SEARCH_AGE_VALUE, e.getMessage());
        }
    }

    @Test
    void shouldPreserveAgesOrder(){
        List<Integer> ages = List.of(24, 30, 18);
        HotelSearch hotelSearch = new HotelSearch(
                "hotel1", LocalDate.now(), LocalDate.now().plusDays(1), ages, null);

        assertEquals(ages, hotelSearch.ages());
    }

    @Test
    void shouldEnsureAgesIsImmutable(){
        HotelSearch hotelSearch = new HotelSearch(
                "hotel1", LocalDate.now(), LocalDate.now().plusDays(1), List.of(24, 30, 18), null);

        assertThrows(UnsupportedOperationException.class, () -> hotelSearch.ages().add(40));
    }
}
