package com.tvmaze.api.client;

import com.tvmaze.api.dto.TvMazeSearchItem;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

import java.util.List;
import java.util.Map;

@Component
public class TvMazeClient {

    private final RestClient restClient;

    public TvMazeClient(RestClient.Builder restClientBuilder) {
        this.restClient = restClientBuilder
                .baseUrl("https://api.tvmaze.com")
                .build();
    }

    public List<TvMazeSearchItem> searchShows(String query) {

        List<TvMazeSearchItem> response = restClient
                .get()
                .uri(uriBuilder -> uriBuilder
                        .path("/search/shows")
                        .queryParam("q", query)
                        .build())
                .retrieve()
                .body(new ParameterizedTypeReference<>() {
                });

        return response != null ? response : List.of();
    }

    public Map<String, Object> getShowById(Long showId) {
        Map<String, Object> response = restClient
                .get()
                .uri("/shows/{showId}", showId)
                .retrieve()
                .body(new ParameterizedTypeReference<>() {
                });

        return response != null ? response : Map.of();
    }
}