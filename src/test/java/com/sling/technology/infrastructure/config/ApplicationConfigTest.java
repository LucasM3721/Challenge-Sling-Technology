package com.sling.technology.infrastructure.config;

import com.sling.technology.application.port.in.CountUseCase;
import com.sling.technology.application.port.in.SearchUseCase;
import com.sling.technology.domain.repository.SearchMessagePublisher;
import com.sling.technology.domain.repository.SearchRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@ExtendWith(MockitoExtension.class)
class ApplicationConfigTest {

    @Mock
    private SearchMessagePublisher searchMessagePublisher;

    @Mock
    private SearchRepository searchRepository;

    private ApplicationConfig applicationConfig;

    @BeforeEach
    void setUp() {
        applicationConfig = new ApplicationConfig();
    }

    @Test
    void shouldCreateSearchUseCaseBean() {
        SearchUseCase searchUseCase = applicationConfig.searchUseCase(searchMessagePublisher);

        assertNotNull(searchUseCase);
    }

    @Test
    void shouldCreateCountUseCaseBean() {
        CountUseCase countUseCase = applicationConfig.countUseCase(searchRepository);

        assertNotNull(countUseCase);
    }

    @Test
    void shouldCreateAllBeans() {
        assertAll(
                () -> assertNotNull(applicationConfig.searchUseCase(searchMessagePublisher)),
                () -> assertNotNull(applicationConfig.countUseCase(searchRepository))
        );
    }
}
