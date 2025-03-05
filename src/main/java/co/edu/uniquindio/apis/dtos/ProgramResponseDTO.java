package co.edu.uniquindio.apis.dtos;

import co.edu.uniquindio.apis.model.Comment;

import java.time.LocalDateTime;
import java.util.List;

public record ProgramResponseDTO (

        Long id,
        String title,
        String description,
        String content,
        String creatorId,
        List<Comment> comments,
        LocalDateTime creationDate

){

}
