package com.tvmaze.api.controller;

import com.tvmaze.api.dto.CommentRequest;
import com.tvmaze.api.dto.StatusResponse;
import com.tvmaze.api.service.CommentService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/shows")
@Validated
public class CommentController {

    private final CommentService commentService;

    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    @PostMapping("/{showId}/comments")
    public ResponseEntity<StatusResponse> createComment(
            @PathVariable
            @Positive
            Long showId,

            @Valid
            @RequestBody
            CommentRequest request
    ) {
        commentService.saveComment(showId, request);
        return ResponseEntity.status(HttpStatus.CREATED).body(new StatusResponse("success"));
    }
}