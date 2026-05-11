package com.sling.technology.infraestructure.adapter.out.persistance;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

import static com.sling.technology.utils.Constants.HOTEL_SEARCH_TABLE_NAME;

@Entity
@Table(name = HOTEL_SEARCH_TABLE_NAME)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SearchEntity {
    @Id
    private String searchId;

    @Column(nullable = false)
    private String hotelId;

    @Column(nullable = false)
    private LocalDate checkIn;

    @Column(nullable = false)
    private LocalDate checkOut;

    @Column(nullable = false)
    private String ages;
}
