package com.tvmaze.api.dto;

import java.util.List;

public record ShowSearchResponse(
        Long id,
        String name,
        String channel,
        String summary,
        List<String> genres
) {

}