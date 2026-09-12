package com.tvmaze.api.repository;

import com.tvmaze.api.document.CommentDocument;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface CommentRepository
        extends MongoRepository<CommentDocument, String> {

}