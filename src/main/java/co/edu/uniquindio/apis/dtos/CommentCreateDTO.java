package co.edu.uniquindio.apis.dtos;

public record CommentCreateDTO
        (
                Long authorId,
                String content,
                Long programId
        ){

}
