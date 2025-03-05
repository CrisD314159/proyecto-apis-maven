package co.edu.uniquindio.apis.dtos;

import co.edu.uniquindio.apis.model.enums.CommentState;

public record CommentDTO (

        String id,
        String authorId,
        String content,
        CommentState state

){

}
