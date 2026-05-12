package com.sling.technology.infrastructure.config;

import com.sling.technology.application.port.in.CountUseCase;
import com.sling.technology.application.port.in.SearchUseCase;
import com.sling.technology.application.service.HotelSearchService;
import com.sling.technology.application.service.SearchCountService;
import com.sling.technology.domain.repository.SearchMessagePublisher;
import com.sling.technology.domain.repository.SearchRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ApplicationConfig {

    @Bean
    public SearchUseCase searchUseCase(SearchMessagePublisher searchMessagePublisher) {
        return new HotelSearchService(searchMessagePublisher);
    }

    @Bean
    public CountUseCase countUseCase(SearchRepository searchRepository) {
        return new SearchCountService(searchRepository);
    }
}
