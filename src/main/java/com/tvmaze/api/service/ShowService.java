package com.tvmaze.api.service;

import com.tvmaze.api.client.TvMazeClient;
import com.tvmaze.api.dto.ShowSearchResponse;
import com.tvmaze.api.dto.TvMazeChannel;
import com.tvmaze.api.dto.TvMazeSearchItem;
import com.tvmaze.api.dto.TvMazeShow;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ShowService {

    private final TvMazeClient tvMazeClient;

    public ShowService(TvMazeClient tvMazeClient) {
        this.tvMazeClient = tvMazeClient;
    }

    public List<ShowSearchResponse> searchShows(String query) {
        return tvMazeClient.searchShows(query)
                .stream()
                .map(TvMazeSearchItem::show)
                .map(this::toSearchResponse)
                .toList();
    }

    private ShowSearchResponse toSearchResponse(TvMazeShow show) {
        return new ShowSearchResponse(
                show.id(),
                show.name(),
                resolveChannel(show),
                show.summary(),
                show.genres()
        );
    }

    private String resolveChannel(TvMazeShow show) {
        TvMazeChannel network = show.network();

        if (network != null) {
            return network.name();
        }

        TvMazeChannel webChannel = show.webChannel();

        if (webChannel != null) {
            return webChannel.name();
        }

        return null;
    }
}