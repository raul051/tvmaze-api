package com.tvmaze.api.document;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Map;

@Document(collection = "shows")
public record ShowDocument(

        @Id
        Long id,

        Map<String, Object> data
) {

}