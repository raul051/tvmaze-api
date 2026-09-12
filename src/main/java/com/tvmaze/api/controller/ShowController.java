package com.tvmaze.api.controller;

import com.tvmaze.api.dto.ShowSearchResponse;
import com.tvmaze.api.service.ShowService;
import jakarta.validation.constraints.NotBlank;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

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
}