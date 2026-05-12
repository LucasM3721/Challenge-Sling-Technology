package com.sling.technology.infrastructure.adapter.in.web;

import com.sling.technology.application.port.in.CountUseCase;
import com.sling.technology.application.port.in.SearchUseCase;
import com.sling.technology.domain.exception.SearchNotFoundException;
import com.sling.technology.domain.model.HotelSearch;
import com.sling.technology.domain.model.SearchCount;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.List;

import static com.sling.technology.domain.exception.DomainMessages.SEARCH_NOT_FOUND;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(SearchController.class)
class SearchControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private SearchUseCase searchUseCase;

    @MockitoBean
    private CountUseCase countUseCase;

    @Test
    void shouldCreateSearchAndReturnCreatedHttp() throws Exception {
        mockMvc.perform(post("/search")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"hotelId\": \"hotelId\", \"checkIn\": \"15/06/2026\", \"checkOut\": \"16/06/2026\", \"ages\": [30]}"))
                .andExpect(status().isCreated());

        verify(searchUseCase, times(1)).execute(any());
    }

    @Test
    void shouldTryToCreateSearchAndReturnBadRequestHttp() throws Exception {
        mockMvc.perform(post("/search")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"hotelId\": null, \"checkIn\": \"15/06/2026\", \"checkOut\": \"16/06/2026\", \"ages\": [30]}"))
                .andExpect(status().isBadRequest());

        verify(searchUseCase, times(0)).execute(any());
    }

    @Test
    void shouldCountAndReturnOkHttp() throws Exception {
        LocalDate tomorrow = LocalDate.now().plusDays(1);
        LocalDate dayAfterTomorrow = LocalDate.now().plusDays(2);

        HotelSearch hotelSearch = new HotelSearch(
                "hotelId",
                tomorrow,
                dayAfterTomorrow,
                List.of(30),
                "searchId"
        );
        SearchCount searchCount = new SearchCount("searchId", hotelSearch, 5L);

        when(countUseCase.execute("searchId")).thenReturn(searchCount);

        mockMvc.perform(get("/count")
                        .param("searchId", "searchId"))
                .andExpect(status().isOk());

        verify(countUseCase, times(1)).execute("searchId");
    }

    @Test
    void shouldReturnNotFoundWhenSearchIdDoesNotExist() throws Exception {
        when(countUseCase.execute("nonExistentId")).thenThrow(new SearchNotFoundException(SEARCH_NOT_FOUND));

        mockMvc.perform(get("/count")
                        .param("searchId", "nonExistentId"))
                .andExpect(status().isNotFound());

        verify(countUseCase, times(1)).execute("nonExistentId");
    }
}
