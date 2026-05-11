package com.sling.technology.infrastructure.adapter.in.web;

import com.sling.technology.application.port.in.CountUseCase;
import com.sling.technology.application.port.in.SearchUseCase;
import com.sling.technology.domain.exception.DomainException;
import com.sling.technology.domain.model.HotelSearch;
import com.sling.technology.domain.model.SearchCount;
import org.junit.jupiter.api.MediaType;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.List;

import static com.sling.technology.utils.Constants.DOMAIN_EXCEPTION_COUNT_SERVICE_SEARCH_ID;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(SearchController.class)
public class SearchControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private SearchUseCase searchUseCase;

    @MockitoBean
    private CountUseCase countUseCase;

    @Test
    void shouldCreateSearchAndReturnCreatedHttp() throws Exception {
        mockMvc.perform(post("/search")
                        .contentType(String.valueOf(MediaType.APPLICATION_JSON))
                        .content("{\"hotelId\": \"hotelId\", \"checkIn\": \"11/05/2026\", \"checkOut\": \"12/05/2026\", \"ages\": [30]}"))
                .andExpect(status().isCreated());

        verify(searchUseCase, times(1)).execute(any());
    }

    @Test
    void shouldTryToCreateSearchAndReturnBadRequestHttp() throws Exception {
        mockMvc.perform(post("/search")
                .contentType(String.valueOf(MediaType.APPLICATION_JSON))
                .content("{\"hotelId\": null, \"checkIn\": \"11/05/2026\", \"checkOut\": \"12/05/2026\", \"ages\": [30]}"))
            .andExpect(status().isBadRequest());

        verify(searchUseCase, times(0)).execute(any());
    }

    @Test
    void shouldCountAndReturnOkHttp() throws Exception {
        HotelSearch hotelSearch = new HotelSearch(
                "hotelId",
                LocalDate.now(),
                LocalDate.now().plusDays(1),
                List.of(30),
                null
        );
        SearchCount searchCount = new SearchCount("searchId", hotelSearch, 5L);

        when(countUseCase.execute("searchId")).thenReturn(searchCount);

        mockMvc.perform(get("/count")
                        .contentType(String.valueOf(MediaType.APPLICATION_JSON))
                        .param("searchId", "searchId"))
                .andExpect(status().isOk());

        verify(countUseCase, times(1)).execute("searchId");
    }

    @Test
    void shouldTryToCountAndReturnBadRequestHttp() throws Exception {
        when(countUseCase.execute("searchId")).thenThrow(new DomainException(DOMAIN_EXCEPTION_COUNT_SERVICE_SEARCH_ID));

        mockMvc.perform(get("/count")
                        .contentType(String.valueOf(MediaType.APPLICATION_JSON))
                        .param("searchId", "searchId"))
                .andExpect(status().isBadRequest());

        verify(countUseCase, times(1)).execute("searchId");
    }
}
