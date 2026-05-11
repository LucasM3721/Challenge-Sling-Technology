package com.sling.technology.domain.model;

import com.sling.technology.domain.exception.DomainException;
import org.apache.commons.lang3.StringUtils;
import org.springframework.util.CollectionUtils;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

import static com.sling.technology.utils.Constants.*;
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
                if (StringUtils.isEmpty(searchId)) {
                        searchId = randomUUID().toString();
                }
                if (StringUtils.isEmpty(hotelId)) {
                        throw new DomainException(DOMAIN_EXCEPTION_HOTEL_SEARCH_HOTEL_ID);
                }
                if (Objects.isNull(checkIn)) {
                        throw new DomainException(DOMAIN_EXCEPTION_HOTEL_SEARCH_CHECK_IN);
                }
                if (Objects.isNull(checkOut)) {
                        throw new DomainException(DOMAIN_EXCEPTION_HOTEL_SEARCH_CHECK_OUT);
                }
                if (checkIn.isAfter(checkOut) || checkIn.isEqual(checkOut)) {
                        throw new DomainException(DOMAIN_EXCEPTION_HOTEL_SEARCH_CHECK_IN_BEFORE_CHECK_OUT);
                }
                if (CollectionUtils.isEmpty(ages)) {
                        throw new DomainException(DOMAIN_EXCEPTION_HOTEL_SEARCH_AGES);
                }
                for (Integer age : ages) {
                        if (age == null || age < 0) {
                                throw new DomainException(DOMAIN_EXCEPTION_HOTEL_SEARCH_AGE_VALUE);
                        }
                }

                ages = List.copyOf(ages);
        }
}
