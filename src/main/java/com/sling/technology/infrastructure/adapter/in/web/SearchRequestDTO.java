package com.sling.technology.infrastructure.adapter.in.web;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;
import java.util.List;

import static com.sling.technology.utils.Constants.*;

public record SearchRequestDTO(
        @NotNull(message = DOMAIN_EXCEPTION_HOTEL_SEARCH_HOTEL_ID)
        @NotEmpty(message = DOMAIN_EXCEPTION_HOTEL_SEARCH_HOTEL_ID)
        String hotelId,

        @NotNull(message = DOMAIN_EXCEPTION_HOTEL_SEARCH_CHECK_IN)
        @JsonFormat(pattern = "dd/MM/yyyy")
        LocalDate checkIn,

        @NotNull(message = DOMAIN_EXCEPTION_HOTEL_SEARCH_CHECK_OUT)
        @JsonFormat(pattern = "dd/MM/yyyy")
        LocalDate checkOut,

        @NotNull(message = DOMAIN_EXCEPTION_HOTEL_SEARCH_AGES)
        @NotEmpty(message = DOMAIN_EXCEPTION_HOTEL_SEARCH_AGES)
        List<@NotNull(message = DOMAIN_EXCEPTION_HOTEL_SEARCH_AGE_VALUE) Integer> ages
) {
}
