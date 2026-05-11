package com.sling.technology.infrastructure.adapter.in.web;

import com.sling.technology.application.port.in.CountUseCase;
import com.sling.technology.application.port.in.SearchUseCase;
import com.sling.technology.domain.model.HotelSearch;
import com.sling.technology.domain.model.SearchCount;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping()
public class SearchController {
    private final SearchUseCase searchUseCase;
    private final CountUseCase countUseCase;

    public SearchController(SearchUseCase searchUseCase, CountUseCase countUseCase) {
        this.searchUseCase = searchUseCase;
        this.countUseCase = countUseCase;
    }

    @PostMapping("/search")
    public ResponseEntity<SearchResponseDTO> search(@Valid @RequestBody SearchRequestDTO request){
        HotelSearch hotelSearch = new HotelSearch(
                request.hotelId(),
                request.checkIn(),
                request.checkOut(),
                request.ages(),
                null
        );

        String searchId = searchUseCase.execute(hotelSearch);

        return ResponseEntity.status(HttpStatus.CREATED).body(new SearchResponseDTO(searchId));
    }

    @GetMapping("/count")
    public ResponseEntity<CountResponseDTO> count(@Valid @RequestParam String hotelId){
        SearchCount result = countUseCase.execute(hotelId);

         SearchRequestDTO searchDto = new SearchRequestDTO(
                result.search().hotelId(),
                result.search().checkIn(),
                result.search().checkOut(),
                result.search().ages()
        );

        CountResponseDTO response = new CountResponseDTO(
                result.searchId(),
                searchDto,
                result.count()
        );

        return ResponseEntity.ok(response);
    }
}
