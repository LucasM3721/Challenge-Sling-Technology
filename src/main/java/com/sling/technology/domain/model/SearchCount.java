package com.sling.technology.domain.model;

public record SearchCount(
        String searchId,
        HotelSearch search,
        Long count) {
}
