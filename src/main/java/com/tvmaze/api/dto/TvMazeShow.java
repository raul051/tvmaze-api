package com.tvmaze.api.dto;

import java.util.List;

public record TvMazeShow(
        Long id,
        String name,
        List<String> genres,
        TvMazeChannel network,
        TvMazeChannel webChannel,
        String summary
) {

}