package com.tvmaze.api.controller;

import com.tvmaze.api.dto.ShowSearchResponse;
import com.tvmaze.api.service.ShowService;
import jakarta.validation.constraints.NotBlank;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import jakarta.validation.constraints.Positive;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/shows")
@Validated
public class ShowController {

    private final ShowService showService;

    public ShowController(ShowService showService) {
        this.showService = showService;
    }

    @GetMapping("/search")
    public List<ShowSearchResponse> searchShows(
            @RequestParam(name = "search_query")
            @NotBlank
            String searchQuery
    ) {
        return showService.searchShows(searchQuery);
    }

    @GetMapping("/{showId}")
    public Map<String, Object> getShowById(
            @PathVariable
            @Positive
            Long showId
    ) {
        return showService.getShowById(showId);
    }

}