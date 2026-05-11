package com.sling.technology.infraestructure.adapter.in.web;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;
import java.util.List;

public record SearchRequestDTO(
        @NotNull(message = "hotelId cannot be null")
        @NotEmpty(message = "hotelId cannot be empty")
        String hotelId,

        @NotNull(message = "checkIn cannot be null")
        @JsonFormat(pattern = "dd/MM/yyyy")
        LocalDate checkIn,

        @NotNull(message = "checkOut cannot be null")
        @JsonFormat(pattern = "dd/MM/yyyy")
        LocalDate checkOut,

        @NotNull(message = "ages cannot be null")
        @NotEmpty(message = "ages cannot be empty")
        List<@NotNull(message = "age cannot be null") Integer> ages
) {
}
