package com.sling.technology.infrastructure.adapter.in.web;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;
import java.util.List;

import static com.sling.technology.utils.Constants.*;

public record SearchRequestDTO(
        @NotNull(message = DOMAIN_EXCEPTION_HOTEL_SEARCH_HOTEL_ID)
        @NotEmpty(message = DOMAIN_EXCEPTION_HOTEL_SEARCH_HOTEL_ID)
        @Schema(description = "Hotel identifier", example = "hotel123")
        String hotelId,

        @NotNull(message = DOMAIN_EXCEPTION_HOTEL_SEARCH_CHECK_IN)
        @JsonFormat(pattern = "dd/MM/yyyy")
        @Schema(description = "Check-in date", example = "11/05/2026", type = "string", format = "date")
        LocalDate checkIn,

        @NotNull(message = DOMAIN_EXCEPTION_HOTEL_SEARCH_CHECK_OUT)
        @JsonFormat(pattern = "dd/MM/yyyy")
        @Schema(description = "Check-out date", example = "18/05/2026", type = "string", format = "date")
        LocalDate checkOut,

        @NotNull(message = DOMAIN_EXCEPTION_HOTEL_SEARCH_AGES)
        @NotEmpty(message = DOMAIN_EXCEPTION_HOTEL_SEARCH_AGES)
        @Schema(description = "List of guest ages", example = "[24, 30, 7]")
        List<@NotNull(message = DOMAIN_EXCEPTION_HOTEL_SEARCH_AGE_VALUE) Integer> ages
) {
}
