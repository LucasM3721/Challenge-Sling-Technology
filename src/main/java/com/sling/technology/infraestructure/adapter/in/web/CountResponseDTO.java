package com.sling.technology.infraestructure.adapter.in.web;

public record CountResponseDTO(
        String searchId,
        SearchRequestDTO search,
        Integer count
) {
}
