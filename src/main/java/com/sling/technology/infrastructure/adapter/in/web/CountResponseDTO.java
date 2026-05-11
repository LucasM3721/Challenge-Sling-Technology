package com.sling.technology.infrastructure.adapter.in.web;

public record CountResponseDTO(
        String searchId,
        SearchRequestDTO search,
        Long count) {
}
