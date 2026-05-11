package com.sling.technology.infraestructure.adapter.out.persistance;

import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;

public interface SpringDataSearchRepository extends JpaRepository<SearchEntity, String> {
    long countByHotelIdAndCheckInAndCheckOutAndAges(String hotelId, LocalDate checkIn, LocalDate checkOut, String ages);
}
