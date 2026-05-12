package com.sling.technology.infrastructure.adapter.in.web;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;
import java.util.List;

import static com.sling.technology.domain.exception.DomainMessages.*;

public record SearchRequestDTO(
        @NotNull(message = HOTEL_ID_REQUIRED)
        @NotEmpty(message = HOTEL_ID_REQUIRED)
        @Schema(description = "Hotel identifier", example = "hotel123")
        String hotelId,

        @NotNull(message = CHECK_IN_REQUIRED)
        @JsonFormat(pattern = "dd/MM/yyyy")
        @Schema(description = "Check-in date", example = "15/06/2026", type = "string", format = "date")
        LocalDate checkIn,

        @NotNull(message = CHECK_OUT_REQUIRED)
        @JsonFormat(pattern = "dd/MM/yyyy")
        @Schema(description = "Check-out date", example = "22/06/2026", type = "string", format = "date")
        LocalDate checkOut,

        @NotNull(message = AGES_REQUIRED)
        @NotEmpty(message = AGES_REQUIRED)
        @Schema(description = "List of guest ages", example = "[24, 30, 7]")
        List<@NotNull(message = AGE_INVALID) Integer> ages
) {
}
