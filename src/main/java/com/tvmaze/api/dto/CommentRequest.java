package com.tvmaze.api.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record CommentRequest(

        @NotBlank(message = "El comentario es obligatorio")
        String comment,

        @NotNull(message = "La calificación es obligatoria")
        @Min(value = 0, message = "La calificación mínima es 0")
        @Max(value = 5, message = "La calificación máxima es 5")
        Integer rating
) {

}