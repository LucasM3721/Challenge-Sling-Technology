package com.sling.technology.domain.model;

import com.sling.technology.domain.exception.DomainException;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

import static com.sling.technology.domain.exception.DomainMessages.*;
import static java.util.UUID.randomUUID;

/**
 * Represents a hotel availability search request.
 * Immutable record with validation in compact constructor.
 */
public record HotelSearch(
        String hotelId,
        LocalDate checkIn,
        LocalDate checkOut,
        List<Integer> ages,
        String searchId
        )
{
        public HotelSearch {
                if (searchId == null || searchId.isBlank()) {
                        searchId = randomUUID().toString();
                }
                if (hotelId == null || hotelId.isBlank()) {
                        throw new DomainException(HOTEL_ID_REQUIRED);
                }
                if (Objects.isNull(checkIn)) {
                        throw new DomainException(CHECK_IN_REQUIRED);
                }
                if (Objects.isNull(checkOut)) {
                        throw new DomainException(CHECK_OUT_REQUIRED);
                }
                if (checkIn.isBefore(LocalDate.now())) {
                        throw new DomainException(CHECK_IN_PAST);
                }
                if (checkIn.isAfter(checkOut) || checkIn.isEqual(checkOut)) {
                        throw new DomainException(CHECK_IN_BEFORE_CHECK_OUT);
                }
                if (ages == null || ages.isEmpty()) {
                        throw new DomainException(AGES_REQUIRED);
                }
                for (Integer age : ages) {
                        if (age == null || age < 0) {
                                throw new DomainException(AGE_INVALID);
                        }
                }
                // Defensive copy to ensure immutability
                ages = List.copyOf(ages);
        }
}
