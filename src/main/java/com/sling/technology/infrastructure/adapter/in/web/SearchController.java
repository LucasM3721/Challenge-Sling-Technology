package com.sling.technology.infrastructure.adapter.in.web;

import com.sling.technology.application.port.in.CountUseCase;
import com.sling.technology.application.port.in.SearchUseCase;
import com.sling.technology.domain.model.HotelSearch;
import com.sling.technology.domain.model.SearchCount;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping()
@Tag(name = "Hotel Search", description = "API for hotel availability searches")
public class SearchController {
    private final SearchUseCase searchUseCase;
    private final CountUseCase countUseCase;

    public SearchController(SearchUseCase searchUseCase, CountUseCase countUseCase) {
        this.searchUseCase = searchUseCase;
        this.countUseCase = countUseCase;
    }

    @Operation(
            summary = "Create a new hotel search",
            description = "Validates the search request and publishes it to Kafka for asynchronous persistence. Returns a unique search ID."
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "201",
                    description = "Search created successfully",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = SearchResponseDTO.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Invalid request payload",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = Map.class)
                    )
            )
    })
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

    @Operation(
            summary = "Count identical searches",
            description = "Returns the count of searches with identical parameters (hotelId, checkIn, checkOut, ages in same order)"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Count retrieved successfully",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = CountResponseDTO.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Search ID not found or invalid",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = Map.class)
                    )
            )
    })
    @GetMapping("/count")
    public ResponseEntity<CountResponseDTO> count(
            @Parameter(
                    description = "The unique search ID returned from POST /search",
                    required = true,
                    example = "34d0c6e0-6282-4002-90c5-a9ad2d3fc635"
            )
            @Valid @RequestParam String searchId){
        SearchCount result = countUseCase.execute(searchId);

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
