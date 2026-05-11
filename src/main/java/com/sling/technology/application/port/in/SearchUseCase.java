package com.sling.technology.application.port.in;

import com.sling.technology.domain.model.HotelSearch;

public interface SearchUseCase {
    String execute(HotelSearch search);
}
