package com.sling.technology.domain.model;

import java.time.LocalDate;
import java.util.List;

public record HotelSearch(
        String hotelId,
        LocalDate checkIn,
        LocalDate checkOut,
        List<Integer> ages,
        String searchId
        )
{

}
