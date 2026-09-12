package com.tvmaze.api.dto;

public record TvMazeSearchItem(
        Double score,
        TvMazeShow show
) {

}