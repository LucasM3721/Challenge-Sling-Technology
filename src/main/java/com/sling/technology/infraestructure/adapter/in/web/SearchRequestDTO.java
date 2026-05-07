package com.sling.technology.infraestructure.adapter.in.web;

import java.time.LocalDate;
import java.util.List;

public record SearchRequestDTO(
        String hotelId,
        LocalDate checkIn,
        LocalDate checkOut,
        List<Integer> ages,
        String searchId
) {
}
