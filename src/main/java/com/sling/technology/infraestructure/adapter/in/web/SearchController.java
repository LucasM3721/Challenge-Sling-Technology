package com.sling.technology.infraestructure.adapter.in.web;

import com.sling.technology.application.service.HotelSearchService;
import com.sling.technology.domain.model.HotelSearch;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class SearchController {
    private final HotelSearchService hotelSearchService;

    public SearchController(HotelSearchService hotelSearchService) {
        this.hotelSearchService = hotelSearchService;
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

        String searchId = hotelSearchService.execute(hotelSearch);


        SearchResponseDTO searchResponseDTO = new SearchResponseDTO(searchId);

        return ResponseEntity.status(HttpStatus.CREATED).body(searchResponseDTO);
    }

    @GetMapping("/count")
    public ResponseEntity<CountResponseDTO> count(@Valid @RequestParam String hotelId){


        return ResponseEntity.ok(new CountResponseDTO("", new SearchRequestDTO("", null, null, null, null), 0));
    }
}
