package com.sling.technology.domain.model;

import com.sling.technology.domain.exception.DomainException;

import java.time.LocalDate;
import java.util.List;

import static java.util.UUID.randomUUID;

public record HotelSearch(
        String hotelId,
        LocalDate checkIn,
        LocalDate checkOut,
        List<Integer> ages,
        String searchId
        )
{
        public HotelSearch {
                if (hotelId == null || hotelId.isEmpty()) {
                        throw new DomainException("hotelId cannot be null or empty");
                }
                if (checkIn == null) {
                        throw new DomainException("checkIn cannot be null");
                }
                if (checkOut == null) {
                        throw new DomainException("checkOut cannot be null");
                }
                if (checkIn.isAfter(checkOut) || checkIn.isEqual(checkOut)) {
                        throw new DomainException("checkIn must be before checkOut");
                }
                if (ages == null || ages.isEmpty()) {
                        throw new DomainException("ages cannot be null or empty");
                }
                for (Integer age : ages) {
                        if (age == null || age < 0) {
                                throw new DomainException("ages must contain valid numbers >= 0");
                        }
                }
                if (searchId == null || searchId.isEmpty()) {
                        searchId = randomUUID().toString();
                }

                ages = List.copyOf(ages);
        }
}
