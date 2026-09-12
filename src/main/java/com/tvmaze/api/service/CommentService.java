package com.tvmaze.api.service;

import com.tvmaze.api.document.CommentDocument;
import com.tvmaze.api.dto.CommentRequest;
import com.tvmaze.api.repository.CommentRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class CommentService {

    private static final Logger log =
            LoggerFactory.getLogger(CommentService.class);

    private final CommentRepository commentRepository;

    public CommentService(CommentRepository commentRepository) {
        this.commentRepository = commentRepository;
    }

    public void saveComment(Long showId, CommentRequest request) {

        CommentDocument document = new CommentDocument(
                UUID.randomUUID().toString(),
                showId,
                request.comment(),
                request.rating()
        );

        commentRepository.save(document);
        log.info("comentario guardado",showId);
    }
}