package com.tvmaze.api.service;

import com.tvmaze.api.client.TvMazeClient;
import com.tvmaze.api.document.ShowDocument;
import com.tvmaze.api.dto.ShowSearchResponse;
import com.tvmaze.api.dto.TvMazeChannel;
import com.tvmaze.api.dto.TvMazeSearchItem;
import com.tvmaze.api.dto.TvMazeShow;
import com.tvmaze.api.repository.ShowRepository;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Map;

@Service
public class ShowService {

    private static final Logger log = LoggerFactory.getLogger(ShowService.class);

    private final TvMazeClient tvMazeClient;
    private final ShowRepository showRepository;

    public ShowService( TvMazeClient tvMazeClient, ShowRepository showRepository) {
        this.tvMazeClient = tvMazeClient;
        this.showRepository = showRepository;
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

    public Map<String, Object> getShowById(Long showId) {
        return showRepository.findById(showId)
                .map(showDocument -> {
                    log.info("se encontro en Mongo", showId);
                    return showDocument.data();
                })
                .orElseGet(() -> {
                    log.info("no se encontro en mongo, se busca en tvmaze", showId);
                    Map<String, Object> show =
                            tvMazeClient.getShowById(showId);
                    showRepository.save(
                            new ShowDocument(showId, show)
                    );
                    log.info("se guardo en mongo", showId);

                    return show;
                });
    }

}