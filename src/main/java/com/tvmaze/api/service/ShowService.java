package com.tvmaze.api.service;

import com.tvmaze.api.client.TvMazeClient;
import com.tvmaze.api.document.ShowDocument;
import com.tvmaze.api.dto.ShowSearchResponse;
import com.tvmaze.api.dto.TvMazeChannel;
import com.tvmaze.api.dto.TvMazeSearchItem;
import com.tvmaze.api.dto.TvMazeShow;
import com.tvmaze.api.repository.ShowRepository;
import com.tvmaze.api.document.CommentDocument;
import com.tvmaze.api.dto.CommentResponse;
import com.tvmaze.api.repository.CommentRepository;

import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class ShowService {

    private static final Logger log = LoggerFactory.getLogger(ShowService.class);

    private final TvMazeClient tvMazeClient;
    private final ShowRepository showRepository;
    private final CommentRepository commentRepository;

    public ShowService(TvMazeClient tvMazeClient, ShowRepository showRepository, CommentRepository commentRepository) {
        this.tvMazeClient = tvMazeClient;
        this.showRepository = showRepository;
        this.commentRepository = commentRepository;
    }

    public List<ShowSearchResponse> searchShows(String query) {
        List<TvMazeShow> shows = tvMazeClient.searchShows(query)
                .stream()
                .map(TvMazeSearchItem::show)
                .toList();

        List<Long> showIds = shows.stream()
                .map(TvMazeShow::id)
                .toList();

        List<CommentDocument> comments =
                commentRepository.findByShowIdIn(showIds);

        Map<Long, List<CommentResponse>> commentsByShowId =
                comments.stream().collect(Collectors.groupingBy(
                                CommentDocument::showId,
                                Collectors.mapping(
                                        this::toCommentResponse,
                                        Collectors.toList()
                                )
                        ));

        return shows.stream()
                .map(show -> toSearchResponse(show,
                        commentsByShowId.getOrDefault(
                                show.id(),
                                List.of()
                        )
                ))
                .toList();
    }

    private ShowSearchResponse toSearchResponse(TvMazeShow show, List<CommentResponse> comments) {
        return new ShowSearchResponse(
                show.id(),
                show.name(),
                resolveChannel(show),
                show.summary(),
                show.genres(),
                comments
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

    private CommentResponse toCommentResponse(CommentDocument comment) {
        return new CommentResponse(comment.comment(),comment.rating());
    }
}