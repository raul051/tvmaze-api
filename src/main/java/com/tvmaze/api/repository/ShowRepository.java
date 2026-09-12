package com.tvmaze.api.repository;

import com.tvmaze.api.document.ShowDocument;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ShowRepository extends MongoRepository<ShowDocument, Long> {

}